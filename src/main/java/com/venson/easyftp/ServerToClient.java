package com.venson.easyftp;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Subclass of AbstractFTPClient that implements storing and retrieving file
 * from an FTP server to local FTP client, and support resume broken transfer.
 *
 * @author venson
 */
public class ServerToClient extends AbstractFTPClient {

    /**
     * The logger of this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(ServerToClient.class);

    /**
     * The FTP client.
     */
    private FTPClient ftp = null;

    /**
     * The FTP information.
     */
    private FTPFileInfoVO ftpVO = null;

    /**
     * The local file.
     */
    private File localFile = null;

    /**
     * The FTP transfer mode.(download)
     */
    private FTPTransferMode transferMode = FTPTransferMode.DOWNLOAD;

    /**
     * Set the FTP transfer mode.(download or upload)
     */
    public void setTransferMode(FTPTransferMode transferMode) {
        this.transferMode = transferMode;
    }

    /**
     * Get the FTP transfer mode.
     */
    public FTPTransferMode getTransferMode() {
        return transferMode;
    }

    /**
     * Constructor method to initial the FTP client and command listener logger.
     */
    public ServerToClient() {
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new FTPLogCommandListener(logger));
    }

    /**
     * Transfer file from FTP server to local.
     *
     * @param remote The FTP file URL.
     * @param local The local file URL.
     * @return The result of transfer.
     */
    public boolean transfer(String remote, String local) {

        // Convert FTP file URL to value object.
        ftpVO = convertFTPUrlToVO(remote);

        // Create local file.
        localFile = new File(local);

        // Do transfer flow.
        return doTransferFlow();
    }

    /**
     * Connect to FTP server.
     *
     * @return The result of connection.
     * @throws IOException
     */
    @Override
    protected boolean connect() throws IOException {

        if (!connect(ftp, ftpVO)) {
            logger.error("Failed to connect to server.");
            return false;
        }

        return true;
    }

    /**
     * Login to FTP server.
     *
     * @return The result of login.
     * @throws IOException
     */
    @Override
    protected boolean login() throws IOException {

        if (!ftp.login(ftpVO.getUserName(), ftpVO.getPassword())) {
            logger.error("Cannot login to server1.");
            return false;
        }

        return true;
    }

    /**
     * Do file transfer process, download or upload is depended on transfer mode.
     *
     * @return The result of transfer.
     * @throws IOException
     */
    @Override
    protected boolean doTransfer() throws IOException {

        if (transferMode == FTPTransferMode.DOWNLOAD) {

            return download();

        } else if (transferMode == FTPTransferMode.UPLOAD) {

            return upload();
        }

        return false;
    }

    /**
     * The download process.
     *
     * @return The result of download
     * @throws IOException
     */
    private boolean download() throws IOException {

        OutputStream output = null;
        try {

            // Set file type to binary.
            ftp.setFileType(FTP.BINARY_FILE_TYPE);

            // Set FTP mode to passive.
            ftp.enterLocalPassiveMode();

            // Check whether the remote file exists.
            FTPFile[] files = ftp.listFiles(ftpVO.getFileFullPath());
            if (files.length == 0) {
                logger.error("Remote file does not exist.");
                return false;
            }

            // When the local file exists.
            if (localFile.exists()) {

                // When it is resume broken transfer mode.
                if (isResumeBroken()) {

                    long localSize = localFile.length();
                    long remoteSize = files[0].getSize();

                    // When file size unreasonable.
                    if (localSize >= remoteSize) {
                        logger.error("Local file exists and its size is large or equal than remote file's size.");
                        return false;

                    } else {

                        // Set resume broken point.
                        ftp.setRestartOffset(localSize);
                    }

                } else {

                    // Delete and recreate the local file.
                    if (!localFile.delete() || !localFile.createNewFile()) {
                        logger.error("Local file exists and cannot recreate it for redownloading.");
                        return false;
                    }
                }

            } else {

                // Create the local path.
                if (!localFile.getParentFile().exists() && !localFile.getParentFile().mkdirs()) {
                    logger.error("Cannot create local file's directory.");
                    return false;
                }

                // Create the local file.
                if (!localFile.createNewFile()) {
                    logger.error("Cannot create local file.");
                    return false;
                }
            }

            // Do download process.
            output = new FileOutputStream(localFile, true);
            return ftp.retrieveFile(ftpVO.getFileFullPath(), output);

        } finally {
            closeQuietly(output);
        }
    }

    /**
     * The upload process.
     *
     * @return The result of upload.
     * @throws IOException
     */
    private boolean upload() throws IOException {

        InputStream input = null;
        try {

            // Set file type to binary.
            ftp.setFileType(FTP.BINARY_FILE_TYPE);

            // Set FTP mode to passive.
            ftp.enterLocalPassiveMode();

            // Check whether the local file exists.
            if (!localFile.exists()) {
                logger.error("Local file does not exist.");
                return false;
            }

            // The remote file.
            FTPFile[] files = ftp.listFiles(ftpVO.getFileFullPath());

            // The file size to skip when resume broken transfer.
            long localSkip = 0L;

            // When the remote exists.
            if (files.length != 0) {

                // When it is resume broken transfer mode.
                if (isResumeBroken()) {

                    long localSize = localFile.length();
                    long remoteSize = files[0].getSize();

                    // When file size unreasonable.
                    if (localSize <= remoteSize) {
                        logger.error("Remote file exists and its size is large or equal than local file's size.");
                        return false;

                    } else {

                        // Set resume broken point.
                        ftp.setRestartOffset(remoteSize);
                        localSkip = remoteSize;
                    }

                } else {

                    // Delete the remote file.
                    if (!ftp.deleteFile(ftpVO.getFileFullPath())) {
                        logger.error("Remote file exists and cannot delete it for reuploading.");
                        return false;
                    }
                }
            }

            // Change the working directory to the parent directory of the remote file.
            if (!changeDir(ftp, ftpVO.getFileDirectory())) {
                logger.error("Change directory failed.");
                return false;
            }

            // Do upload process.
            input = new FileInputStream(localFile);
            if (localSkip != input.skip(localSkip)) {
                logger.error("Skip uploaded file size failed.");
                return false;
            }
            return ftp.storeFile(ftpVO.getFileName(), input);

        } finally {
            closeQuietly(input);
        }
    }

    /**
     * Logout and disconnect from FTP server.
     */
    @Override
    protected void logoutAndDisconnect() {

        try {
            if (ftp.isConnected()) {
                ftp.logout();
            }
        } catch (IOException e) {
            logger.error("ftp logout failed.", e);
        }

        try {
            if (ftp.isConnected()) {
                ftp.disconnect();
            }
        } catch (IOException e) {
            logger.error("ftp disconnect failed.", e);
        }
    }

    /**
     * Close the stream and do not care the result.
     *
     * @param closeable The stream.
     */
    private void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            logger.debug("closeQuietly", e);
        }
    }
}
