package com.venson.easyftp;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.venson.easyftp.AbstractFTPClient;
import com.venson.easyftp.FTPFileInfoVO;
import com.venson.easyftp.FTPTransferMode;
import com.venson.easyftp.ServerToClient;

/**
 * The test class of ServerToClient.
 *
 * @author venson
 */
@RunWith(PowerMockRunner.class)
public class ServerToClientTest {

    /**
     * Test default behavior.
     */
    @Test
    public void testDefaultBehavior001() {

        // =================== Before  ===================

        // ===================  Input  ===================

        // =================== Process ===================
        ServerToClient client = new ServerToClient();

        // =================== Output  ===================
        Assert.assertEquals(FTPTransferMode.DOWNLOAD, client.getTransferMode());

        // ===================  After  ===================
    }

    /**
     * Change default behavior.
     */
    @Test
    public void testChangeBehavior001() {

        // =================== Before  ===================

        // ===================  Input  ===================

        // =================== Process ===================
        ServerToClient client = new ServerToClient();
        client.setTransferMode(FTPTransferMode.UPLOAD);

        // =================== Output  ===================
        Assert.assertEquals(FTPTransferMode.UPLOAD, client.getTransferMode());

        // ===================  After  ===================
    }

    /**
     * File transfer success.
     *
     * @throws Exception
     */
    @Test
    public void testTransfer001() throws Exception {

        // =================== Before  ===================
        ServerToClient client = PowerMock.createPartialMock(ServerToClient.class, "doTransferFlow");

        // mock step 1
        PowerMock.expectPrivate(client, "doTransferFlow").andReturn(true).times(1);

        PowerMock.replay(ServerToClient.class, client);

        // ===================  Input  ===================
        String remote = "ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml";
        String local = "c:/04_VOD_20110411093958_001047556.xml";

        // =================== Process ===================
        boolean result = client.transfer(remote, local);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToClient.class, client);
    }

    /**
     * File transfer failure.
     *
     * @throws Exception
     */
    @Test
    public void testTransfer002() throws Exception {

        // =================== Before  ===================
        ServerToClient client = PowerMock.createPartialMock(ServerToClient.class, "doTransferFlow");

        // mock step 1
        PowerMock.expectPrivate(client, "doTransferFlow").andReturn(false).times(1);

        PowerMock.replay(ServerToClient.class, client);

        // ===================  Input  ===================
        String remote = "ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml";
        String local = "c:/04_VOD_20110411093958_001047556.xml";

        // =================== Process ===================
        boolean result = client.transfer(remote, local);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToClient.class, client);
    }

    /**
     * Connection success.
     *
     * @throws Exception
     */
    @Test
    public void testConnect001() throws Exception {

        // =================== Before  ===================
        ServerToClient client = PowerMock.createPartialMock(ServerToClient.class, "connect", FTPClient.class, FTPFileInfoVO.class);

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToClient.class.getDeclaredField("ftp");
        field2.setAccessible(true);
        field2.set(client, ftp);

        // mock step 1
        PowerMock.expectPrivate(client, "connect", ftp, vo).andReturn(true).times(1);

        PowerMock.replay(ServerToClient.class, client);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("connect");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToClient.class, client);
    }

    /**
     * Connection failure.
     *
     * @throws Exception
     */
    @Test
    public void testConnect002() throws Exception {

        // =================== Before  ===================
        ServerToClient client = PowerMock.createPartialMock(ServerToClient.class, "connect", FTPClient.class, FTPFileInfoVO.class);

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToClient.class.getDeclaredField("ftp");
        field2.setAccessible(true);
        field2.set(client, ftp);

        // mock step 1
        PowerMock.expectPrivate(client, "connect", ftp, vo).andReturn(false).times(1);

        PowerMock.replay(ServerToClient.class, client);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("connect");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToClient.class, client);
    }

    /**
     * Login success.
     *
     * @throws Exception
     */
    @Test
    public void testLogin001() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToClient.class.getDeclaredField("ftp");
        field2.setAccessible(true);
        field2.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.login("username", "password")).andReturn(true).times(1);

        EasyMock.replay(ftp);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("login");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        EasyMock.verify(ftp);
    }

    /**
     * Login failure.
     *
     * @throws Exception
     */
    @Test
    public void testLogin002() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToClient.class.getDeclaredField("ftp");
        field2.setAccessible(true);
        field2.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.login("username", "password")).andReturn(false).times(1);

        EasyMock.replay(ftp);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("login");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(ftp);
    }

    /**
     * Download process, when remote file not exists, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferDownload001() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        FTPFile[] ftpFiles = {};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        EasyMock.replay(localFile, ftp);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(localFile, ftp);
    }

    /**
     * Download process, when remote file exists, and local file not exists, but fail to create parent directory,
     * failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferDownload002() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        FTPFile ftpFile = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles = {ftpFile};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 4
        EasyMock.expect(localFile.exists()).andReturn(false).times(1);

        // mock step 5
        File localFileParentPath = PowerMock.createStrictMock(File.class);
        EasyMock.expect(localFile.getParentFile()).andReturn(localFileParentPath).times(1);
        EasyMock.expect(localFileParentPath.exists()).andReturn(false).times(1);
        EasyMock.expect(localFile.getParentFile()).andReturn(localFileParentPath).times(1);
        EasyMock.expect(localFileParentPath.mkdirs()).andReturn(false).times(1);

        EasyMock.replay(ftp, ftpFile, localFile, localFileParentPath);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(ftp, ftpFile, localFile, localFileParentPath);
    }

    /**
     * Download process, when remote file exists, and local file not exists, and local file parent directory exists,
     * but fail to create local file, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferDownload003() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        FTPFile ftpFile = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles = {ftpFile};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 4
        EasyMock.expect(localFile.exists()).andReturn(false).times(1);

        // mock step 5
        File localFileParentPath = PowerMock.createStrictMock(File.class);
        EasyMock.expect(localFile.getParentFile()).andReturn(localFileParentPath).times(1);
        EasyMock.expect(localFileParentPath.exists()).andReturn(true).times(1);

        // mock step 6
        EasyMock.expect(localFile.createNewFile()).andReturn(false).times(1);

        EasyMock.replay(ftp, ftpFile, localFile, localFileParentPath);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(ftp, ftpFile, localFile, localFileParentPath);
    }

    /**
     * Download process, when remote file exists, and local file not exists, and create local file parent directory success,
     * but fail to create local file, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferDownload004() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        FTPFile ftpFile = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles = {ftpFile};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 4
        EasyMock.expect(localFile.exists()).andReturn(false).times(1);

        // mock step 5
        File localFileParentPath = PowerMock.createStrictMock(File.class);
        EasyMock.expect(localFile.getParentFile()).andReturn(localFileParentPath).times(1);
        EasyMock.expect(localFileParentPath.exists()).andReturn(false).times(1);
        EasyMock.expect(localFile.getParentFile()).andReturn(localFileParentPath).times(1);
        EasyMock.expect(localFileParentPath.mkdirs()).andReturn(true).times(1);

        // mock step 6
        EasyMock.expect(localFile.createNewFile()).andReturn(false).times(1);

        EasyMock.replay(ftp, ftpFile, localFile, localFileParentPath);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(ftp, ftpFile, localFile, localFileParentPath);
    }

    /**
     * Download process, when remote file exists, and local file not exists, and create local file success,
     * but fail to retrieve remote file, failure is expected.
     *
     * @throws Exception
     */
    @Test
    @PrepareForTest({ ServerToClient.class })
    public void testDoTransferDownload005() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        FTPFile ftpFile = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles = {ftpFile};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 4
        EasyMock.expect(localFile.exists()).andReturn(false).times(1);

        // mock step 5
        File localFileParentPath = PowerMock.createStrictMock(File.class);
        EasyMock.expect(localFile.getParentFile()).andReturn(localFileParentPath).times(1);
        EasyMock.expect(localFileParentPath.exists()).andReturn(false).times(1);
        EasyMock.expect(localFile.getParentFile()).andReturn(localFileParentPath).times(1);
        EasyMock.expect(localFileParentPath.mkdirs()).andReturn(true).times(1);

        // mock step 6
        EasyMock.expect(localFile.createNewFile()).andReturn(true).times(1);

        // mock step 7
        FileOutputStream fos = PowerMock.createStrictMock(FileOutputStream.class);
        PowerMock.expectStrictNew(FileOutputStream.class, localFile, true).andReturn(fos).times(1);
        EasyMock.expect(ftp.retrieveFile("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml", fos)).andReturn(false).times(1);

        // mock step 8
        fos.close();
        EasyMock.expectLastCall().times(1);

        PowerMock.replay(ftp, ftpFile, localFile, localFileParentPath, FileOutputStream.class, fos);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        PowerMock.verify(ftp, ftpFile, localFile, localFileParentPath, FileOutputStream.class, fos);
    }

    /**
     * Download process, when remote file exists, and local file not exists, and create local file success,
     * and retrieve remote file success, success is expected.
     *
     * @throws Exception
     */
    @Test
    @PrepareForTest({ ServerToClient.class })
    public void testDoTransferDownload006() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        FTPFile ftpFile = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles = {ftpFile};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 4
        EasyMock.expect(localFile.exists()).andReturn(false);

        // mock step 5
        File localFileParentPath = PowerMock.createStrictMock(File.class);
        EasyMock.expect(localFile.getParentFile()).andReturn(localFileParentPath).times(1);
        EasyMock.expect(localFileParentPath.exists()).andReturn(false).times(1);
        EasyMock.expect(localFile.getParentFile()).andReturn(localFileParentPath).times(1);
        EasyMock.expect(localFileParentPath.mkdirs()).andReturn(true).times(1);

        // mock step 6
        EasyMock.expect(localFile.createNewFile()).andReturn(true).times(1);

        // mock step 7
        FileOutputStream fos = PowerMock.createStrictMock(FileOutputStream.class);
        PowerMock.expectStrictNew(FileOutputStream.class, localFile, true).andReturn(fos).times(1);
        EasyMock.expect(ftp.retrieveFile("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml", fos)).andReturn(true).times(1);

        // mock step 8
        fos.close();
        EasyMock.expectLastCall().times(1);

        PowerMock.replay(ftp, ftpFile, localFile, localFileParentPath, FileOutputStream.class, fos);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        PowerMock.verify(ftp, ftpFile, localFile, localFileParentPath, FileOutputStream.class, fos);
    }

    /**
     * Download process, when remote file exists, and local file exists, and is not resume broken transfer mode,
     * but fail to delete local file, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferDownload007() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();
        client.setResumeBroken(false);

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        FTPFile ftpFile = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles = {ftpFile};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 4
        EasyMock.expect(localFile.exists()).andReturn(true).times(1);

        // mock step 5
        EasyMock.expect(localFile.delete()).andReturn(false).times(1);

        EasyMock.replay(ftp, ftpFile, localFile);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(ftp, ftpFile, localFile);
    }

    /**
     * Download process, when remote file exists, and local file exists, and is not resume broken transfer mode,
     * and delete local file success, but fail to recreate local file, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferDownload008() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();
        client.setResumeBroken(false);

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        FTPFile ftpFile = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles = {ftpFile};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 4
        EasyMock.expect(localFile.exists()).andReturn(true).times(1);

        // mock step 5
        EasyMock.expect(localFile.delete()).andReturn(true).times(1);
        EasyMock.expect(localFile.createNewFile()).andReturn(false).times(1);

        EasyMock.replay(ftp, ftpFile, localFile);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(ftp, ftpFile, localFile);
    }

    /**
     * Download process, when remote file exists, and local file exists, and is not resume broken transfer mode,
     * and delete local file success, and recreate local file success, and retrieve remote file success,
     * success is expected.
     *
     * @throws Exception
     */
    @Test
    @PrepareForTest({ ServerToClient.class })
    public void testDoTransferDownload009() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();
        client.setResumeBroken(false);

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        FTPFile ftpFile = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles = {ftpFile};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 4
        EasyMock.expect(localFile.exists()).andReturn(true).times(1);

        // mock step 5
        EasyMock.expect(localFile.delete()).andReturn(true).times(1);
        EasyMock.expect(localFile.createNewFile()).andReturn(true).times(1);

        // mock step 6
        FileOutputStream fos = PowerMock.createStrictMock(FileOutputStream.class);
        PowerMock.expectStrictNew(FileOutputStream.class, localFile, true).andReturn(fos).times(1);
        EasyMock.expect(ftp.retrieveFile("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml", fos)).andReturn(true).times(1);

        // mock step 7
        fos.close();
        EasyMock.expectLastCall().times(1);

        PowerMock.replay(ftp, ftpFile, localFile, FileOutputStream.class, fos);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        PowerMock.verify(ftp, ftpFile, localFile, FileOutputStream.class, fos);
    }

    /**
     * Download process, when remote file exists, and local file exists, and is resume broken transfer mode,
     * but local file size is larger than remote file size, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferDownload010() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        FTPFile ftpFile = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles = {ftpFile};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 4
        EasyMock.expect(localFile.exists()).andReturn(true).times(1);

        // mock step 5
        EasyMock.expect(localFile.length()).andReturn(100L).times(1);
        EasyMock.expect(ftpFile.getSize()).andReturn(99L).times(1);

        EasyMock.replay(ftp, ftpFile, localFile);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(ftp, ftpFile, localFile);
    }

    /**
     * Download process, when remote file exists, and local file exists, and is resume broken transfer mode,
     * but local file size is equal to remote file size, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferDownload011() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        FTPFile ftpFile = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles = {ftpFile};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 4
        EasyMock.expect(localFile.exists()).andReturn(true).times(1);

        // mock step 5
        EasyMock.expect(localFile.length()).andReturn(100L).times(1);
        EasyMock.expect(ftpFile.getSize()).andReturn(100L).times(1);

        EasyMock.replay(ftp, ftpFile, localFile);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(ftp, ftpFile, localFile);
    }

    /**
     * Download process, when remote file exists, and local file exists, and is resume broken transfer mode,
     * and local file size is smaller than remote file size, and retrieve remote file success, success is expected.
     *
     * @throws Exception
     */
    @Test
    @PrepareForTest({ ServerToClient.class })
    public void testDoTransferDownload012() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        FTPFile ftpFile = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles = {ftpFile};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 4
        EasyMock.expect(localFile.exists()).andReturn(true).times(1);

        // mock step 5
        EasyMock.expect(localFile.length()).andReturn(99L).times(1);
        EasyMock.expect(ftpFile.getSize()).andReturn(100L).times(1);

        // mock step 6
        ftp.setRestartOffset(99L);
        EasyMock.expectLastCall().times(1);

        // mock step 7
        FileOutputStream fos = PowerMock.createStrictMock(FileOutputStream.class);
        PowerMock.expectStrictNew(FileOutputStream.class, localFile, true).andReturn(fos).times(1);
        EasyMock.expect(ftp.retrieveFile("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml", fos)).andReturn(true).times(1);

        // mock step 8
        fos.close();
        EasyMock.expectLastCall().times(1);

        PowerMock.replay(ftp, ftpFile, localFile, FileOutputStream.class, fos);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        PowerMock.verify(ftp, ftpFile, localFile, FileOutputStream.class, fos);
    }

    /**
     * Upload process, when local file not exists, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferUpload001() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();
        client.setTransferMode(FTPTransferMode.UPLOAD);

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        EasyMock.expect(localFile.exists()).andReturn(false).times(1);

        EasyMock.replay(ftp, localFile);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(ftp, localFile);
    }

    /**
     * Upload process, when local file exists, and remote file not exists, but fail to create remote file parent directory,
     * failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferUpload002() throws Exception {

        // =================== Before  ===================
        ServerToClient client = PowerMock.createPartialMock(ServerToClient.class, "changeDir");
        client.setTransferMode(FTPTransferMode.UPLOAD);

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        EasyMock.expect(localFile.exists()).andReturn(true).times(1);

        // mock step 4
        FTPFile[] ftpFiles = {};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 5
        PowerMock.expectPrivate(client, "changeDir", ftp, "/ftp/cmdfile").andReturn(false).times(1);

        PowerMock.replay(ServerToClient.class, client, ftp, localFile);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToClient.class, client, ftp, localFile);
    }

    /**
     * Upload process, when local file exists, and remote file not exists, and create remote file parent directory success,
     * but fail to store remote file, failure is expected.
     *
     * @throws Exception
     */
    @Test
    @PrepareForTest({ ServerToClient.class })
    public void testDoTransferUpload003() throws Exception {

        // =================== Before  ===================
        ServerToClient client = PowerMock.createPartialMock(ServerToClient.class, "changeDir");
        client.setTransferMode(FTPTransferMode.UPLOAD);

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        EasyMock.expect(localFile.exists()).andReturn(true).times(1);

        // mock step 4
        FTPFile[] ftpFiles = {};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 5
        PowerMock.expectPrivate(client, "changeDir", ftp, "/ftp/cmdfile").andReturn(true).times(1);

        // mock step 6
        FileInputStream fis = PowerMock.createStrictMock(FileInputStream.class);
        PowerMock.expectStrictNew(FileInputStream.class, localFile).andReturn(fis).times(1);
        EasyMock.expect(fis.skip(0L)).andReturn(0L).times(1);

        // mock step 7
        EasyMock.expect(ftp.storeFile("04_VOD_20110411093958_001047556.xml", fis)).andReturn(false).times(1);

        // mock step 8
        fis.close();
        EasyMock.expectLastCall().times(1);

        PowerMock.replay(ServerToClient.class, client, ftp, localFile, FileInputStream.class, fis);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToClient.class, client, ftp, localFile, FileInputStream.class, fis);
    }

    /**
     * Upload process, when local file exists, and remote file not exists, and create remote file parent directory success,
     * and store remote file success, success is expected.
     *
     * @throws Exception
     */
    @Test
    @PrepareForTest({ ServerToClient.class })
    public void testDoTransferUpload004() throws Exception {

        // =================== Before  ===================
        ServerToClient client = PowerMock.createPartialMock(ServerToClient.class, "changeDir");
        client.setTransferMode(FTPTransferMode.UPLOAD);

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        EasyMock.expect(localFile.exists()).andReturn(true).times(1);

        // mock step 4
        FTPFile[] ftpFiles = {};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 5
        PowerMock.expectPrivate(client, "changeDir", ftp, "/ftp/cmdfile").andReturn(true).times(1);

        // mock step 6
        FileInputStream fis = PowerMock.createStrictMock(FileInputStream.class);
        PowerMock.expectStrictNew(FileInputStream.class, localFile).andReturn(fis).times(1);
        EasyMock.expect(fis.skip(0L)).andReturn(0L).times(1);

        // mock step 7
        EasyMock.expect(ftp.storeFile("04_VOD_20110411093958_001047556.xml", fis)).andReturn(true).times(1);

        // mock step 8
        fis.close();
        EasyMock.expectLastCall().times(1);

        PowerMock.replay(ServerToClient.class, client, ftp, localFile, FileInputStream.class, fis);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToClient.class, client, ftp, localFile, FileInputStream.class, fis);
    }

    /**
     * Upload process, when local file exists, and remote file exists, and is not resume broken transfer mode,
     * but fail to delete remote file, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferUpload005() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();
        client.setTransferMode(FTPTransferMode.UPLOAD);
        client.setResumeBroken(false);

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        EasyMock.expect(localFile.exists()).andReturn(true).times(1);

        // mock step 4
        FTPFile ftpFile = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles = {ftpFile};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 5
        EasyMock.expect(ftp.deleteFile("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(false).times(1);

        EasyMock.replay(ftp, localFile);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(ftp, localFile);
    }

    /**
     * Upload process, when local file exists, and remote file exists, and is not resume broken transfer mode,
     * and delete remote file success, and store remote file success, success is expected.
     *
     * @throws Exception
     */
    @Test
    @PrepareForTest({ ServerToClient.class })
    public void testDoTransferUpload006() throws Exception {

        // =================== Before  ===================
        ServerToClient client = PowerMock.createPartialMock(ServerToClient.class, "changeDir");
        client.setTransferMode(FTPTransferMode.UPLOAD);
        client.setResumeBroken(false);

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        EasyMock.expect(localFile.exists()).andReturn(true).times(1);

        // mock step 4
        FTPFile ftpFile = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles = {ftpFile};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 5
        EasyMock.expect(ftp.deleteFile("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(true).times(1);

        // mock step 6
        PowerMock.expectPrivate(client, "changeDir", ftp, "/ftp/cmdfile").andReturn(true).times(1);

        // mock step 7
        FileInputStream fis = PowerMock.createStrictMock(FileInputStream.class);
        PowerMock.expectStrictNew(FileInputStream.class, localFile).andReturn(fis).times(1);
        EasyMock.expect(fis.skip(0L)).andReturn(0L).times(1);

        // mock step 8
        EasyMock.expect(ftp.storeFile("04_VOD_20110411093958_001047556.xml", fis)).andReturn(true).times(1);

        // mock step 8
        fis.close();
        EasyMock.expectLastCall().times(1);

        PowerMock.replay(ServerToClient.class, client, ftp, localFile, FileInputStream.class, fis);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToClient.class, client, ftp, localFile, FileInputStream.class, fis);
    }

    /**
     * Upload process, when local file exists, and remote file exists, and is resume broken transfer mode,
     * local file size is smaller than remote file size, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferUpload007() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();
        client.setTransferMode(FTPTransferMode.UPLOAD);

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        EasyMock.expect(localFile.exists()).andReturn(true).times(1);

        // mock step 4
        FTPFile ftpFile = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles = {ftpFile};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 5
        EasyMock.expect(localFile.length()).andReturn(99L).times(1);
        EasyMock.expect(ftpFile.getSize()).andReturn(100L).times(1);

        EasyMock.replay(ftp, localFile, ftpFile);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(ftp, localFile, ftpFile);
    }

    /**
     * Upload process, when local file exists, and remote file exists, and is resume broken transfer mode,
     * local file size is equal to remote file size, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferUpload008() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();
        client.setTransferMode(FTPTransferMode.UPLOAD);

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        EasyMock.expect(localFile.exists()).andReturn(true).times(1);

        // mock step 4
        FTPFile ftpFile = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles = {ftpFile};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 5
        EasyMock.expect(localFile.length()).andReturn(100L).times(1);
        EasyMock.expect(ftpFile.getSize()).andReturn(100L).times(1);

        EasyMock.replay(ftp, localFile, ftpFile);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(ftp, localFile, ftpFile);
    }

    /**
     * Upload process, when local file exists, and remote file exists, and is resume broken transfer mode,
     * local file size is larger than remote file size, but fail to set local file skip size, failure is expected.
     *
     * @throws Exception
     */
    @Test
    @PrepareForTest({ ServerToClient.class })
    public void testDoTransferUpload009() throws Exception {

        // =================== Before  ===================
        ServerToClient client = PowerMock.createPartialMock(ServerToClient.class, "changeDir");
        client.setTransferMode(FTPTransferMode.UPLOAD);
        client.setResumeBroken(true);

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        EasyMock.expect(localFile.exists()).andReturn(true).times(1);

        // mock step 4
        FTPFile ftpFile = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles = {ftpFile};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 5
        EasyMock.expect(localFile.length()).andReturn(100L).times(1);
        EasyMock.expect(ftpFile.getSize()).andReturn(99L).times(1);

        // mock step 6
        ftp.setRestartOffset(99L);
        EasyMock.expectLastCall().times(1);

        // mock step 7
        PowerMock.expectPrivate(client, "changeDir", ftp, "/ftp/cmdfile").andReturn(true).times(1);

        // mock step 8
        FileInputStream fis = PowerMock.createStrictMock(FileInputStream.class);
        PowerMock.expectStrictNew(FileInputStream.class, localFile).andReturn(fis).times(1);
        EasyMock.expect(fis.skip(99L)).andReturn(0L).times(1);

        // mock step 9
        fis.close();
        EasyMock.expectLastCall();

        PowerMock.replay(ServerToClient.class, client, ftp, localFile, ftpFile, FileInputStream.class, fis);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToClient.class, client, ftp, localFile, ftpFile, FileInputStream.class, fis);
    }

    /**
     * Upload process, when local file exists, and remote file exists, and is resume broken transfer mode,
     * local file size is larger than remote file size, and set local file skip size success, and store remote file success,
     * success is expected.
     *
     * @throws Exception
     */
    @Test
    @PrepareForTest({ ServerToClient.class })
    public void testDoTransferUpload010() throws Exception {

        // =================== Before  ===================
        ServerToClient client = PowerMock.createPartialMock(ServerToClient.class, "changeDir");
        client.setTransferMode(FTPTransferMode.UPLOAD);
        client.setResumeBroken(true);

        // ftpVO
        FTPFileInfoVO vo = AbstractFTPClient.convertFTPUrlToVO("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml");
        Field field1 = ServerToClient.class.getDeclaredField("ftpVO");
        field1.setAccessible(true);
        field1.set(client, vo);

        // localFile
        File localFile = PowerMock.createStrictMock(File.class);
        Field field2 = ServerToClient.class.getDeclaredField("localFile");
        field2.setAccessible(true);
        field2.set(client, localFile);

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field3 = ServerToClient.class.getDeclaredField("ftp");
        field3.setAccessible(true);
        field3.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 2
        ftp.enterLocalPassiveMode();
        EasyMock.expectLastCall().times(1);

        // mock step 3
        EasyMock.expect(localFile.exists()).andReturn(true).times(1);

        // mock step 4
        FTPFile ftpFile = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles = {ftpFile};
        EasyMock.expect(ftp.listFiles("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml")).andReturn(ftpFiles).times(1);

        // mock step 5
        EasyMock.expect(localFile.length()).andReturn(100L).times(1);
        EasyMock.expect(ftpFile.getSize()).andReturn(99L).times(1);

        // mock step 6
        ftp.setRestartOffset(99L);
        EasyMock.expectLastCall().times(1);

        // mock step 7
        PowerMock.expectPrivate(client, "changeDir", ftp, "/ftp/cmdfile").andReturn(true).times(1);

        // mock step 8
        FileInputStream fis = PowerMock.createStrictMock(FileInputStream.class);
        PowerMock.expectStrictNew(FileInputStream.class, localFile).andReturn(fis).times(1);
        EasyMock.expect(fis.skip(99L)).andReturn(99L).times(1);

        // mock step 9
        EasyMock.expect(ftp.storeFile("04_VOD_20110411093958_001047556.xml", fis)).andReturn(true).times(1);

        // mock step 10
        fis.close();
        EasyMock.expectLastCall();

        PowerMock.replay(ServerToClient.class, client, ftp, localFile, ftpFile, FileInputStream.class, fis);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToClient.class, client, ftp, localFile, ftpFile, FileInputStream.class, fis);
    }

    /**
     * When transfer mode is set to null, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferOther001() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();
        client.setTransferMode(null);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
    }

    /**
     * When FTP Client is not connected to server, logout and disconnect are not to be executed.
     *
     * @throws Exception
     */
    @Test
    public void testLogoutAndDisconnect001() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field = ServerToClient.class.getDeclaredField("ftp");
        field.setAccessible(true);
        field.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.isConnected()).andReturn(false).times(1);

        // mock step 2
        EasyMock.expect(ftp.isConnected()).andReturn(false).times(1);

        EasyMock.replay(ftp);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("logoutAndDisconnect");
        method.invoke(client);

        // =================== Output  ===================

        // ===================  After  ===================
        EasyMock.verify(ftp);
    }

    /**
     * When FTP Client is connected to server, logout and disconnect are to be executed.
     *
     * @throws Exception
     */
    @Test
    public void testLogoutAndDisconnect002() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();

        // ftp
        FTPClient ftp = PowerMock.createStrictMock(FTPClient.class);
        Field field = ServerToClient.class.getDeclaredField("ftp");
        field.setAccessible(true);
        field.set(client, ftp);

        // mock step 1
        EasyMock.expect(ftp.isConnected()).andReturn(true).times(1);

        // mock step 2
        EasyMock.expect(ftp.logout()).andReturn(true).times(1);

        // mock step 3
        EasyMock.expect(ftp.isConnected()).andReturn(true).times(1);

        // mock step 4
        ftp.disconnect();
        EasyMock.expectLastCall().times(1);

        EasyMock.replay(ftp);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("logoutAndDisconnect");
        method.invoke(client);

        // =================== Output  ===================

        // ===================  After  ===================
        EasyMock.verify(ftp);
    }

    /**
     * When input is null, close is not to be executed.
     *
     * @throws Exception
     */
    @Test
    public void testCloseQuietly001() throws Exception {

        // =================== Before  ===================
        ServerToClient client = new ServerToClient();

        // ===================  Input  ===================
        Closeable closeable = null;

        // =================== Process ===================
        Method method = ServerToClient.class.getDeclaredMethod("closeQuietly", Closeable.class);
        method.setAccessible(true);
        method.invoke(client, closeable);

        // =================== Output  ===================

        // ===================  After  ===================
    }
}
