package com.venson.easyftp;

import java.io.IOException;
import java.net.InetAddress;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Subclass of AbstractFTPClient that implements a server to server file transfer
 * that transfers a file from server1 to server2.
 *
 * @author venson
 */
public class ServerToServer extends AbstractFTPClient {

    /**
     * The logger of this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(ServerToServer.class);

    /**
     * The FTP client of server1.
     */
    private FTPClient ftp1 = null;

    /**
     * The FTP information of server1.
     */
    private FTPFileInfoVO ftp1VO = null;

    /**
     * The FTP client of server2.
     */
    private FTPClient ftp2 = null;

    /**
     * The FTP information of server2.
     */
    private FTPFileInfoVO ftp2VO = null;

    /**
     * Constructor method to initial the FTP client and command listener logger.
     */
    public ServerToServer() {
        ftp1 = new FTPClient();
        ftp2 = new FTPClient();
        ftp1.addProtocolCommandListener(new FTPLogCommandListener(logger));
        ftp2.addProtocolCommandListener(new FTPLogCommandListener(logger));
    }

    /**
     * Set the FTP encoding.
     *
     * @param encoding1 The FTP encoding of server1.
     * @param encoding2 The FTP encoding of server2.
     */
    public void setControlEncoding(String encoding1, String encoding2) {
        ftp1.setControlEncoding(encoding1);
        ftp2.setControlEncoding(encoding2);
    }

    /**
     * Transfer file from FTP server1 to server2.
     *
     * @param server1Url The FTP file URL of server1.
     * @param server2Url The FTP file URL of server2.
     * @return The result of transfer.
     */
    public boolean transfer(String server1Url, String server2Url) {

        // Convert FTP file URL of server1 to value object.
        ftp1VO = convertFTPUrlToVO(server1Url);

        // Convert FTP file URL of server2 to value object.
        ftp2VO = convertFTPUrlToVO(server2Url);

        // Do transfer flow.
        return doTransferFlow();
    }

    /**
     * Connect to both of FTP server.
     *
     * @return The result of connection.
     * @throws IOException
     */
    @Override
    protected boolean connect() throws IOException {

        if (!connect(ftp1, ftp1VO)) {
            logger.error("Failed to connect to server1.");
            return false;
        }

        if (!connect(ftp2, ftp2VO)) {
            logger.error("Failed to connect to server2.");
            return false;
        }

        return true;
    }

    /**
     * Login to both of FTP server.
     *
     * @return The result of login.
     * @throws IOException
     */
    @Override
    protected boolean login() throws IOException {

        if (!ftp1.login(ftp1VO.getUserName(), ftp1VO.getPassword())) {
            logger.error("Cannot login to server1.");
            return false;
        }

        if (!ftp2.login(ftp2VO.getUserName(), ftp2VO.getPassword())) {
            logger.error("Cannot login to server2.");
            return false;
        }

        return true;
    }

    /**
     * Do transfer process.
     *
     * @return The result of transfer.
     * @throws IOException
     */
    @Override
    protected boolean doTransfer() throws IOException {

        // The remote file.
        FTPFile[] files1 = ftp1.listFiles(ftp1VO.getFileFullPath());
        FTPFile[] files2 = ftp2.listFiles(ftp2VO.getFileFullPath());

        // When the FTP file of server1 dose not exist.
        if (files1.length == 0) {
            logger.error("Specified file does not exist in ftp1.");
            return false;

        } else {

            // When the FTP file of server2 exists.
            if (files2.length != 0) {

                // When it is resume broken transfer mode.
                if (isResumeBroken()) {

                    long ftp1Size = files1[0].getSize();
                    long ftp2Size = files2[0].getSize();

                    // When file size unreasonable.
                    if (ftp1Size <= ftp2Size) {
                        logger.error("Ftp2 file exists and its size is large or equal than ftp1 file's size.");
                        return false;
                    } else {

                        // Set resume broken point.
                        ftp1.setRestartOffset(ftp2Size);
                        ftp2.setRestartOffset(ftp2Size);
                    }

                } else {

                    // Delete the FTP file of server2.
                    if (!ftp2.deleteFile(ftp2VO.getFileFullPath())) {
                        logger.error("Ftp2 file exists and cannot delete it for retransferring.");
                        return false;
                    }
                }
            }
        }

        // Change the working directory to the parent directory of the FTP file of server2.
        if (!changeDir(ftp2, ftp2VO.getFileDirectory())) {
            logger.error("Change directory failed.");
            return false;
        }

        // Set file type to binary.
        ftp1.setFileType(FTP.BINARY_FILE_TYPE);
        ftp2.setFileType(FTP.BINARY_FILE_TYPE);

        // Set FTP mode.
        ftp2.enterRemotePassiveMode();
        ftp1.enterRemoteActiveMode(InetAddress.getByName(ftp2.getPassiveHost()),
            ftp2.getPassivePort());

        // Do transfer process.
        if (ftp1.remoteRetrieve(ftp1VO.getFileFullPath()) && ftp2.remoteStore(ftp2VO.getFileName())) {

            ftp1.completePendingCommand();
            ftp2.completePendingCommand();
            return true;
        } else {
            logger.error("Couldn't initiate transfer. Check that filenames are valid.");
            return false;
        }
    }

    /**
     * Logout and disconnect from both of FTP server.
     */
    @Override
    protected void logoutAndDisconnect() {

        try {
            if (ftp1.isConnected()) {
                ftp1.logout();
            }
        } catch (IOException e) {
            logger.error("ftp1 logout failed.", e);
        }

        try {
            if (ftp2.isConnected()) {
                ftp2.logout();
            }
        } catch (IOException e) {
            logger.error("ftp2 logout failed.", e);
        }

        try {
            if (ftp1.isConnected()) {
                ftp1.disconnect();
            }
        } catch (IOException e) {
            logger.error("ftp1 disconnect failed.", e);
        }

        try {
            if (ftp2.isConnected()) {
                ftp2.disconnect();
            }
        } catch (IOException e) {
            logger.error("ftp2 disconnect failed.", e);
        }
    }
}
