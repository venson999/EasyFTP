package com.venson.easyftp;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.modules.junit4.PowerMockRunner;

import com.venson.easyftp.AbstractFTPClient;
import com.venson.easyftp.FTPFileInfoVO;
import com.venson.easyftp.ServerToServer;

/**
 * The test class of ServerToServer.
 *
 * @author venson
 */
@RunWith(PowerMockRunner.class)
public class ServerToServerTest {

    /**
     * Test FTP client encoding setting.
     *
     * @throws Exception
     */
    @Test
    public void testSetControlEncoding001() throws Exception {

        // =================== Before  ===================
        ServerToServer client = new ServerToServer();

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field1 = ServerToServer.class.getDeclaredField("ftp1");
        field1.setAccessible(true);
        field1.set(client, ftp1);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp2");
        field2.setAccessible(true);
        field2.set(client, ftp2);

        // mock step 1
        ftp1.setControlEncoding("UTF-8");
        EasyMock.expectLastCall().times(1);
        ftp2.setControlEncoding("UTF-16");
        EasyMock.expectLastCall().times(1);

        EasyMock.replay(ftp1, ftp2);

        // ===================  Input  ===================
        String encoding1 = "UTF-8";
        String encoding2 = "UTF-16";

        // =================== Process ===================
        client.setControlEncoding(encoding1, encoding2);

        // =================== Output  ===================

        // ===================  After  ===================
        EasyMock.verify(ftp1, ftp2);
    }

    /**
     * File transfer success, success is expected.
     *
     * @throws Exception
     */
    @Test
    public void testTransfer001() throws Exception {

        // =================== Before  ===================
        ServerToServer client = PowerMock.createPartialMock(ServerToServer.class, "doTransferFlow");

        // mock step 1
        PowerMock.expectPrivate(client, "doTransferFlow").andReturn(true).times(1);

        PowerMock.replay(ServerToServer.class, client);

        // ===================  Input  ===================
        String server1Url = "ftp://username1:password1@192.168.19.251/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml";
        String server2Url = "ftp://username2:password2@192.168.19.252/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml";

        // =================== Process ===================
        boolean result = client.transfer(server1Url, server2Url);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToServer.class, client);
    }

    /**
     * File transfer failure, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testTransfer002() throws Exception {

        // =================== Before  ===================
        ServerToServer client = PowerMock.createPartialMock(ServerToServer.class, "doTransferFlow");

        // mock step 1
        PowerMock.expectPrivate(client, "doTransferFlow").andReturn(false).times(1);

        PowerMock.replay(ServerToServer.class, client);

        // ===================  Input  ===================
        String server1Url = "ftp://username1:password1@192.168.19.251/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml";
        String server2Url = "ftp://username2:password2@192.168.19.252/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml";

        // =================== Process ===================
        boolean result = client.transfer(server1Url, server2Url);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToServer.class, client);
    }

    /**
     * FTP client1 fail to connect to server, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testConnect001() throws Exception {

        // =================== Before  ===================
        ServerToServer client = PowerMock.createPartialMock(ServerToServer.class, "connect", FTPClient.class, FTPFileInfoVO.class);

        // ftp1VO
        FTPFileInfoVO vo1 = AbstractFTPClient.convertFTPUrlToVO("ftp://username1:password1@192.168.19.251/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml");
        Field field1 = ServerToServer.class.getDeclaredField("ftp1VO");
        field1.setAccessible(true);
        field1.set(client, vo1);

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp1");
        field2.setAccessible(true);
        field2.set(client, ftp1);

        // ftp2VO
        FTPFileInfoVO vo2 = AbstractFTPClient.convertFTPUrlToVO("ftp://username2:password2@192.168.19.252/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml");
        Field field3 = ServerToServer.class.getDeclaredField("ftp2VO");
        field3.setAccessible(true);
        field3.set(client, vo2);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field4 = ServerToServer.class.getDeclaredField("ftp2");
        field4.setAccessible(true);
        field4.set(client, ftp2);

        // mock step 1
        PowerMock.expectPrivate(client, "connect", ftp1, vo1).andReturn(false).times(1);

        PowerMock.replay(ServerToServer.class, client, ftp1, ftp2);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToServer.class.getDeclaredMethod("connect");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToServer.class, client, ftp1, ftp2);
    }

    /**
     * FTP client1 connect to server success, but FTP client2 fail to connect to server, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testConnect002() throws Exception {

        // =================== Before  ===================
        ServerToServer client = PowerMock.createPartialMock(ServerToServer.class, "connect", FTPClient.class, FTPFileInfoVO.class);

        // ftp1VO
        FTPFileInfoVO vo1 = AbstractFTPClient.convertFTPUrlToVO("ftp://username1:password1@192.168.19.251/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml");
        Field field1 = ServerToServer.class.getDeclaredField("ftp1VO");
        field1.setAccessible(true);
        field1.set(client, vo1);

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp1");
        field2.setAccessible(true);
        field2.set(client, ftp1);

        // ftp2VO
        FTPFileInfoVO vo2 = AbstractFTPClient.convertFTPUrlToVO("ftp://username2:password2@192.168.19.252/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml");
        Field field3 = ServerToServer.class.getDeclaredField("ftp2VO");
        field3.setAccessible(true);
        field3.set(client, vo2);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field4 = ServerToServer.class.getDeclaredField("ftp2");
        field4.setAccessible(true);
        field4.set(client, ftp2);

        // mock step 1
        PowerMock.expectPrivate(client, "connect", ftp1, vo1).andReturn(true).times(1);

        // mock step 2
        PowerMock.expectPrivate(client, "connect", ftp2, vo2).andReturn(false).times(1);

        PowerMock.replay(ServerToServer.class, client, ftp1, ftp2);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToServer.class.getDeclaredMethod("connect");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToServer.class, client, ftp1, ftp2);
    }

    /**
     * Both of FTP client connect to server success, success is expected.
     *
     * @throws Exception
     */
    @Test
    public void testConnect003() throws Exception {

        // =================== Before  ===================
        ServerToServer client = PowerMock.createPartialMock(ServerToServer.class, "connect", FTPClient.class, FTPFileInfoVO.class);

        // ftp1VO
        FTPFileInfoVO vo1 = AbstractFTPClient.convertFTPUrlToVO("ftp://username1:password1@192.168.19.251/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml");
        Field field1 = ServerToServer.class.getDeclaredField("ftp1VO");
        field1.setAccessible(true);
        field1.set(client, vo1);

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp1");
        field2.setAccessible(true);
        field2.set(client, ftp1);

        // ftp2VO
        FTPFileInfoVO vo2 = AbstractFTPClient.convertFTPUrlToVO("ftp://username2:password2@192.168.19.252/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml");
        Field field3 = ServerToServer.class.getDeclaredField("ftp2VO");
        field3.setAccessible(true);
        field3.set(client, vo2);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field4 = ServerToServer.class.getDeclaredField("ftp2");
        field4.setAccessible(true);
        field4.set(client, ftp2);

        // mock step 1
        PowerMock.expectPrivate(client, "connect", ftp1, vo1).andReturn(true).times(1);

        // mock step 2
        PowerMock.expectPrivate(client, "connect", ftp2, vo2).andReturn(true).times(1);

        PowerMock.replay(ServerToServer.class, client, ftp1, ftp2);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToServer.class.getDeclaredMethod("connect");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToServer.class, client, ftp1, ftp2);
    }

    /**
     * FTP client1 fail to login to server, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testLogin001() throws Exception {

        // =================== Before  ===================
        ServerToServer client = new ServerToServer();

        // ftp1VO
        FTPFileInfoVO vo1 = AbstractFTPClient.convertFTPUrlToVO("ftp://username1:password1@192.168.19.251/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml");
        Field field1 = ServerToServer.class.getDeclaredField("ftp1VO");
        field1.setAccessible(true);
        field1.set(client, vo1);

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp1");
        field2.setAccessible(true);
        field2.set(client, ftp1);

        // ftp2VO
        FTPFileInfoVO vo2 = AbstractFTPClient.convertFTPUrlToVO("ftp://username2:password2@192.168.19.252/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml");
        Field field3 = ServerToServer.class.getDeclaredField("ftp2VO");
        field3.setAccessible(true);
        field3.set(client, vo2);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field4 = ServerToServer.class.getDeclaredField("ftp2");
        field4.setAccessible(true);
        field4.set(client, ftp2);

        // mock step 1
        EasyMock.expect(ftp1.login("username1", "password1")).andReturn(false).times(1);

        EasyMock.replay(ftp1, ftp2);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToServer.class.getDeclaredMethod("login");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(ftp1, ftp2);
    }

    /**
     * FTP client1 login to server success, but FTP client2 fail to login to server, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testLogin002() throws Exception {

        // =================== Before  ===================
        ServerToServer client = new ServerToServer();

        // ftp1VO
        FTPFileInfoVO vo1 = AbstractFTPClient.convertFTPUrlToVO("ftp://username1:password1@192.168.19.251/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml");
        Field field1 = ServerToServer.class.getDeclaredField("ftp1VO");
        field1.setAccessible(true);
        field1.set(client, vo1);

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp1");
        field2.setAccessible(true);
        field2.set(client, ftp1);

        // ftp2VO
        FTPFileInfoVO vo2 = AbstractFTPClient.convertFTPUrlToVO("ftp://username2:password2@192.168.19.252/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml");
        Field field3 = ServerToServer.class.getDeclaredField("ftp2VO");
        field3.setAccessible(true);
        field3.set(client, vo2);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field4 = ServerToServer.class.getDeclaredField("ftp2");
        field4.setAccessible(true);
        field4.set(client, ftp2);

        // mock step 1
        EasyMock.expect(ftp1.login("username1", "password1")).andReturn(true).times(1);

        // mock step 2
        EasyMock.expect(ftp2.login("username2", "password2")).andReturn(false).times(1);

        EasyMock.replay(ftp1, ftp2);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToServer.class.getDeclaredMethod("login");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(ftp1, ftp2);
    }

    /**
     * Both of FTP client login to server success, success is expected.
     *
     * @throws Exception
     */
    @Test
    public void testLogin003() throws Exception {

        // =================== Before  ===================
        ServerToServer client = new ServerToServer();

        // ftp1VO
        FTPFileInfoVO vo1 = AbstractFTPClient.convertFTPUrlToVO("ftp://username1:password1@192.168.19.251/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml");
        Field field1 = ServerToServer.class.getDeclaredField("ftp1VO");
        field1.setAccessible(true);
        field1.set(client, vo1);

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp1");
        field2.setAccessible(true);
        field2.set(client, ftp1);

        // ftp2VO
        FTPFileInfoVO vo2 = AbstractFTPClient.convertFTPUrlToVO("ftp://username2:password2@192.168.19.252/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml");
        Field field3 = ServerToServer.class.getDeclaredField("ftp2VO");
        field3.setAccessible(true);
        field3.set(client, vo2);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field4 = ServerToServer.class.getDeclaredField("ftp2");
        field4.setAccessible(true);
        field4.set(client, ftp2);

        // mock step 1
        EasyMock.expect(ftp1.login("username1", "password1")).andReturn(true).times(1);

        // mock step 2
        EasyMock.expect(ftp2.login("username2", "password2")).andReturn(true).times(1);

        EasyMock.replay(ftp1, ftp2);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToServer.class.getDeclaredMethod("login");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        EasyMock.verify(ftp1, ftp2);
    }

    /**
     * FTP1 file not exists, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransfer001() throws Exception {

        // =================== Before  ===================
        ServerToServer client = new ServerToServer();

        // ftp1VO
        FTPFileInfoVO vo1 = AbstractFTPClient.convertFTPUrlToVO("ftp://username1:password1@192.168.19.251/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml");
        Field field1 = ServerToServer.class.getDeclaredField("ftp1VO");
        field1.setAccessible(true);
        field1.set(client, vo1);

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp1");
        field2.setAccessible(true);
        field2.set(client, ftp1);

        // ftp2VO
        FTPFileInfoVO vo2 = AbstractFTPClient.convertFTPUrlToVO("ftp://username2:password2@192.168.19.252/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml");
        Field field3 = ServerToServer.class.getDeclaredField("ftp2VO");
        field3.setAccessible(true);
        field3.set(client, vo2);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field4 = ServerToServer.class.getDeclaredField("ftp2");
        field4.setAccessible(true);
        field4.set(client, ftp2);

        // mock step 1
        FTPFile[] ftpFiles1 = {};
        EasyMock.expect(ftp1.listFiles("/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml")).andReturn(ftpFiles1).times(1);

        // mock step 2
        FTPFile[] ftpFiles2 = {};
        EasyMock.expect(ftp2.listFiles("/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml")).andReturn(ftpFiles2).times(1);

        EasyMock.replay(ftp1, ftp2);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToServer.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(ftp1, ftp2);
    }

    /**
     * FTP1 file exists, FTP2 file not exists, but fail to create directory, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransfer002() throws Exception {

        // =================== Before  ===================
        ServerToServer client = PowerMock.createPartialMock(ServerToServer.class, "changeDir", FTPClient.class, String.class);

        // ftp1VO
        FTPFileInfoVO vo1 = AbstractFTPClient.convertFTPUrlToVO("ftp://username1:password1@192.168.19.251/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml");
        Field field1 = ServerToServer.class.getDeclaredField("ftp1VO");
        field1.setAccessible(true);
        field1.set(client, vo1);

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp1");
        field2.setAccessible(true);
        field2.set(client, ftp1);

        // ftp2VO
        FTPFileInfoVO vo2 = AbstractFTPClient.convertFTPUrlToVO("ftp://username2:password2@192.168.19.252/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml");
        Field field3 = ServerToServer.class.getDeclaredField("ftp2VO");
        field3.setAccessible(true);
        field3.set(client, vo2);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field4 = ServerToServer.class.getDeclaredField("ftp2");
        field4.setAccessible(true);
        field4.set(client, ftp2);

        // mock step 1
        FTPFile ftpFile1 = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles1 = {ftpFile1};
        EasyMock.expect(ftp1.listFiles("/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml")).andReturn(ftpFiles1).times(1);

        // mock step 2
        FTPFile[] ftpFiles2 = {};
        EasyMock.expect(ftp2.listFiles("/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml")).andReturn(ftpFiles2).times(1);

        // mock step 3
        PowerMock.expectPrivate(client, "changeDir", ftp2, "/ftp2/cmdfile2").andReturn(false).times(1);

        PowerMock.replay(ServerToServer.class, client, ftp1, ftp2, ftpFile1);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToServer.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToServer.class, client, ftp1, ftp2, ftpFile1);
    }

    /**
     * FTP1 file exists, FTP2 file not exists, and create directory success, but fail to transfer file from FTP1, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransfer003() throws Exception {

        // =================== Before  ===================
        ServerToServer client = PowerMock.createPartialMock(ServerToServer.class, "changeDir", FTPClient.class, String.class);

        // ftp1VO
        FTPFileInfoVO vo1 = AbstractFTPClient.convertFTPUrlToVO("ftp://username1:password1@192.168.19.251/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml");
        Field field1 = ServerToServer.class.getDeclaredField("ftp1VO");
        field1.setAccessible(true);
        field1.set(client, vo1);

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp1");
        field2.setAccessible(true);
        field2.set(client, ftp1);

        // ftp2VO
        FTPFileInfoVO vo2 = AbstractFTPClient.convertFTPUrlToVO("ftp://username2:password2@192.168.19.252/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml");
        Field field3 = ServerToServer.class.getDeclaredField("ftp2VO");
        field3.setAccessible(true);
        field3.set(client, vo2);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field4 = ServerToServer.class.getDeclaredField("ftp2");
        field4.setAccessible(true);
        field4.set(client, ftp2);

        // mock step 1
        FTPFile ftpFile1 = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles1 = {ftpFile1};
        EasyMock.expect(ftp1.listFiles("/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml")).andReturn(ftpFiles1).times(1);

        // mock step 2
        FTPFile[] ftpFiles2 = {};
        EasyMock.expect(ftp2.listFiles("/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml")).andReturn(ftpFiles2).times(1);

        // mock step 3
        PowerMock.expectPrivate(client, "changeDir", ftp2, "/ftp2/cmdfile2").andReturn(true).times(1);

        // mock step 4
        EasyMock.expect(ftp1.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);
        EasyMock.expect(ftp2.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 5
        EasyMock.expect(ftp2.enterRemotePassiveMode()).andReturn(true).times(1);
        EasyMock.expect(ftp2.getPassiveHost()).andReturn("1.1.1.1").times(1);
        EasyMock.expect(ftp2.getPassivePort()).andReturn(123).times(1);
        EasyMock.expect(ftp1.enterRemoteActiveMode(InetAddress.getByName("1.1.1.1"), 123)).andReturn(true).times(1);

        // mock step 6
        EasyMock.expect(ftp1.remoteRetrieve("/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml")).andReturn(false).times(1);

        PowerMock.replay(ServerToServer.class, client, ftp1, ftp2, ftpFile1);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToServer.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToServer.class, client, ftp1, ftp2, ftpFile1);
    }

    /**
     * FTP1 file exists, FTP2 file not exists, and create directory success, but fail to transfer file to FTP2, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransfer004() throws Exception {

        // =================== Before  ===================
        ServerToServer client = PowerMock.createPartialMock(ServerToServer.class, "changeDir", FTPClient.class, String.class);

        // ftp1VO
        FTPFileInfoVO vo1 = AbstractFTPClient.convertFTPUrlToVO("ftp://username1:password1@192.168.19.251/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml");
        Field field1 = ServerToServer.class.getDeclaredField("ftp1VO");
        field1.setAccessible(true);
        field1.set(client, vo1);

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp1");
        field2.setAccessible(true);
        field2.set(client, ftp1);

        // ftp2VO
        FTPFileInfoVO vo2 = AbstractFTPClient.convertFTPUrlToVO("ftp://username2:password2@192.168.19.252/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml");
        Field field3 = ServerToServer.class.getDeclaredField("ftp2VO");
        field3.setAccessible(true);
        field3.set(client, vo2);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field4 = ServerToServer.class.getDeclaredField("ftp2");
        field4.setAccessible(true);
        field4.set(client, ftp2);

        // mock step 1
        FTPFile ftpFile1 = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles1 = {ftpFile1};
        EasyMock.expect(ftp1.listFiles("/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml")).andReturn(ftpFiles1).times(1);

        // mock step 2
        FTPFile[] ftpFiles2 = {};
        EasyMock.expect(ftp2.listFiles("/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml")).andReturn(ftpFiles2).times(1);

        // mock step 3
        PowerMock.expectPrivate(client, "changeDir", ftp2, "/ftp2/cmdfile2").andReturn(true).times(1);

        // mock step 4
        EasyMock.expect(ftp1.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);
        EasyMock.expect(ftp2.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 5
        EasyMock.expect(ftp2.enterRemotePassiveMode()).andReturn(true).times(1);
        EasyMock.expect(ftp2.getPassiveHost()).andReturn("1.1.1.1").times(1);
        EasyMock.expect(ftp2.getPassivePort()).andReturn(123).times(1);
        EasyMock.expect(ftp1.enterRemoteActiveMode(InetAddress.getByName("1.1.1.1"), 123)).andReturn(true).times(1);

        // mock step 6
        EasyMock.expect(ftp1.remoteRetrieve("/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml")).andReturn(true).times(1);
        EasyMock.expect(ftp2.remoteStore("04_VOD_20110411093958_0010475562.xml")).andReturn(false).times(1);

        PowerMock.replay(ServerToServer.class, client, ftp1, ftp2, ftpFile1);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToServer.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToServer.class, client, ftp1, ftp2, ftpFile1);
    }

    /**
     * FTP1 file exists, FTP2 file not exists, and create directory success, file transfer success, success is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransfer005() throws Exception {

        // =================== Before  ===================
        ServerToServer client = PowerMock.createPartialMock(ServerToServer.class, "changeDir", FTPClient.class, String.class);

        // ftp1VO
        FTPFileInfoVO vo1 = AbstractFTPClient.convertFTPUrlToVO("ftp://username1:password1@192.168.19.251/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml");
        Field field1 = ServerToServer.class.getDeclaredField("ftp1VO");
        field1.setAccessible(true);
        field1.set(client, vo1);

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp1");
        field2.setAccessible(true);
        field2.set(client, ftp1);

        // ftp2VO
        FTPFileInfoVO vo2 = AbstractFTPClient.convertFTPUrlToVO("ftp://username2:password2@192.168.19.252/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml");
        Field field3 = ServerToServer.class.getDeclaredField("ftp2VO");
        field3.setAccessible(true);
        field3.set(client, vo2);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field4 = ServerToServer.class.getDeclaredField("ftp2");
        field4.setAccessible(true);
        field4.set(client, ftp2);

        // mock step 1
        FTPFile ftpFile1 = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles1 = {ftpFile1};
        EasyMock.expect(ftp1.listFiles("/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml")).andReturn(ftpFiles1).times(1);

        // mock step 2
        FTPFile[] ftpFiles2 = {};
        EasyMock.expect(ftp2.listFiles("/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml")).andReturn(ftpFiles2).times(1);

        // mock step 3
        PowerMock.expectPrivate(client, "changeDir", ftp2, "/ftp2/cmdfile2").andReturn(true).times(1);

        // mock step 4
        EasyMock.expect(ftp1.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);
        EasyMock.expect(ftp2.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 5
        EasyMock.expect(ftp2.enterRemotePassiveMode()).andReturn(true).times(1);
        EasyMock.expect(ftp2.getPassiveHost()).andReturn("1.1.1.1").times(1);
        EasyMock.expect(ftp2.getPassivePort()).andReturn(123).times(1);
        EasyMock.expect(ftp1.enterRemoteActiveMode(InetAddress.getByName("1.1.1.1"), 123)).andReturn(true).times(1);

        // mock step 6
        EasyMock.expect(ftp1.remoteRetrieve("/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml")).andReturn(true).times(1);
        EasyMock.expect(ftp2.remoteStore("04_VOD_20110411093958_0010475562.xml")).andReturn(true).times(1);

        // mock step 7
        EasyMock.expect(ftp1.completePendingCommand()).andReturn(true).times(1);
        EasyMock.expect(ftp2.completePendingCommand()).andReturn(true).times(1);

        PowerMock.replay(ServerToServer.class, client, ftp1, ftp2, ftpFile1);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToServer.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToServer.class, client, ftp1, ftp2, ftpFile1);
    }

    /**
     * FTP1 file exists, FTP2 file exists, and is not resume broken transfer mode, but fail to delete FTP2 file, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransfer006() throws Exception {

        // =================== Before  ===================
        ServerToServer client = new ServerToServer();

        // ftp1VO
        FTPFileInfoVO vo1 = AbstractFTPClient.convertFTPUrlToVO("ftp://username1:password1@192.168.19.251/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml");
        Field field1 = ServerToServer.class.getDeclaredField("ftp1VO");
        field1.setAccessible(true);
        field1.set(client, vo1);

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp1");
        field2.setAccessible(true);
        field2.set(client, ftp1);

        // ftp2VO
        FTPFileInfoVO vo2 = AbstractFTPClient.convertFTPUrlToVO("ftp://username2:password2@192.168.19.252/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml");
        Field field3 = ServerToServer.class.getDeclaredField("ftp2VO");
        field3.setAccessible(true);
        field3.set(client, vo2);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field4 = ServerToServer.class.getDeclaredField("ftp2");
        field4.setAccessible(true);
        field4.set(client, ftp2);

        // mock step 1
        FTPFile ftpFile1 = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles1 = {ftpFile1};
        EasyMock.expect(ftp1.listFiles("/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml")).andReturn(ftpFiles1).times(1);

        // mock step 2
        FTPFile ftpFile2 = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles2 = {ftpFile2};
        EasyMock.expect(ftp2.listFiles("/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml")).andReturn(ftpFiles2).times(1);

        // mock step 3
        client.setResumeBroken(false);

        // mock step 4
        EasyMock.expect(ftp2.deleteFile("/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml")).andReturn(false).times(1);

        EasyMock.replay(ftp1, ftp2, ftpFile1, ftpFile2);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToServer.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(ftp1, ftp2, ftpFile1, ftpFile2);
    }

    /**
     * FTP1 file exists, FTP2 file exists, and is not resume broken transfer mode, and delete FTP2 file success,
     * and file transfer success, success is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransfer007() throws Exception {

        // =================== Before  ===================
        ServerToServer client = PowerMock.createPartialMock(ServerToServer.class, "changeDir", FTPClient.class, String.class);

        // ftp1VO
        FTPFileInfoVO vo1 = AbstractFTPClient.convertFTPUrlToVO("ftp://username1:password1@192.168.19.251/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml");
        Field field1 = ServerToServer.class.getDeclaredField("ftp1VO");
        field1.setAccessible(true);
        field1.set(client, vo1);

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp1");
        field2.setAccessible(true);
        field2.set(client, ftp1);

        // ftp2VO
        FTPFileInfoVO vo2 = AbstractFTPClient.convertFTPUrlToVO("ftp://username2:password2@192.168.19.252/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml");
        Field field3 = ServerToServer.class.getDeclaredField("ftp2VO");
        field3.setAccessible(true);
        field3.set(client, vo2);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field4 = ServerToServer.class.getDeclaredField("ftp2");
        field4.setAccessible(true);
        field4.set(client, ftp2);

        // mock step 1
        FTPFile ftpFile1 = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles1 = {ftpFile1};
        EasyMock.expect(ftp1.listFiles("/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml")).andReturn(ftpFiles1).times(1);

        // mock step 2
        FTPFile ftpFile2 = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles2 = {ftpFile2};
        EasyMock.expect(ftp2.listFiles("/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml")).andReturn(ftpFiles2).times(1);

        // mock step 3
        client.setResumeBroken(false);

        // mock step 4
        EasyMock.expect(ftp2.deleteFile("/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml")).andReturn(true).times(1);

        // mock step 5
        PowerMock.expectPrivate(client, "changeDir", ftp2, "/ftp2/cmdfile2").andReturn(true).times(1);

        // mock step 6
        EasyMock.expect(ftp1.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);
        EasyMock.expect(ftp2.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 7
        EasyMock.expect(ftp2.enterRemotePassiveMode()).andReturn(true).times(1);
        EasyMock.expect(ftp2.getPassiveHost()).andReturn("1.1.1.1").times(1);
        EasyMock.expect(ftp2.getPassivePort()).andReturn(123).times(1);
        EasyMock.expect(ftp1.enterRemoteActiveMode(InetAddress.getByName("1.1.1.1"), 123)).andReturn(true).times(1);

        // mock step 8
        EasyMock.expect(ftp1.remoteRetrieve("/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml")).andReturn(true).times(1);
        EasyMock.expect(ftp2.remoteStore("04_VOD_20110411093958_0010475562.xml")).andReturn(true).times(1);

        // mock step 9
        EasyMock.expect(ftp1.completePendingCommand()).andReturn(true).times(1);
        EasyMock.expect(ftp2.completePendingCommand()).andReturn(true).times(1);

        PowerMock.replay(ServerToServer.class, client, ftp1, ftp2, ftpFile1, ftpFile2);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToServer.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToServer.class, client, ftp1, ftp2, ftpFile1, ftpFile2);
    }

    /**
     * FTP1 file exists, FTP2 file exists, and is resume broken transfer mode, but FTP1 file size smaller than FTP2 file size,
     * failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransfer008() throws Exception {

        // =================== Before  ===================
        ServerToServer client = new ServerToServer();

        // ftp1VO
        FTPFileInfoVO vo1 = AbstractFTPClient.convertFTPUrlToVO("ftp://username1:password1@192.168.19.251/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml");
        Field field1 = ServerToServer.class.getDeclaredField("ftp1VO");
        field1.setAccessible(true);
        field1.set(client, vo1);

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp1");
        field2.setAccessible(true);
        field2.set(client, ftp1);

        // ftp2VO
        FTPFileInfoVO vo2 = AbstractFTPClient.convertFTPUrlToVO("ftp://username2:password2@192.168.19.252/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml");
        Field field3 = ServerToServer.class.getDeclaredField("ftp2VO");
        field3.setAccessible(true);
        field3.set(client, vo2);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field4 = ServerToServer.class.getDeclaredField("ftp2");
        field4.setAccessible(true);
        field4.set(client, ftp2);

        // mock step 1
        FTPFile ftpFile1 = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles1 = {ftpFile1};
        EasyMock.expect(ftp1.listFiles("/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml")).andReturn(ftpFiles1).times(1);

        // mock step 2
        FTPFile ftpFile2 = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles2 = {ftpFile2};
        EasyMock.expect(ftp2.listFiles("/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml")).andReturn(ftpFiles2).times(1);

        // mock step 3
        client.setResumeBroken(true);

        // mock step 4
        EasyMock.expect(ftpFile1.getSize()).andReturn(99L).times(1);
        EasyMock.expect(ftpFile2.getSize()).andReturn(100L).times(1);

        EasyMock.replay(ftp1, ftp2, ftpFile1, ftpFile2);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToServer.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(ftp1, ftp2, ftpFile1, ftpFile2);
    }

    /**
     * FTP1 file exists, FTP2 file exists, and is resume broken transfer mode, but FTP1 file size equal to FTP2 file size,
     * failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransfer009() throws Exception {

        // =================== Before  ===================
        ServerToServer client = new ServerToServer();

        // ftp1VO
        FTPFileInfoVO vo1 = AbstractFTPClient.convertFTPUrlToVO("ftp://username1:password1@192.168.19.251/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml");
        Field field1 = ServerToServer.class.getDeclaredField("ftp1VO");
        field1.setAccessible(true);
        field1.set(client, vo1);

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp1");
        field2.setAccessible(true);
        field2.set(client, ftp1);

        // ftp2VO
        FTPFileInfoVO vo2 = AbstractFTPClient.convertFTPUrlToVO("ftp://username2:password2@192.168.19.252/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml");
        Field field3 = ServerToServer.class.getDeclaredField("ftp2VO");
        field3.setAccessible(true);
        field3.set(client, vo2);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field4 = ServerToServer.class.getDeclaredField("ftp2");
        field4.setAccessible(true);
        field4.set(client, ftp2);

        // mock step 1
        FTPFile ftpFile1 = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles1 = {ftpFile1};
        EasyMock.expect(ftp1.listFiles("/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml")).andReturn(ftpFiles1).times(1);

        // mock step 2
        FTPFile ftpFile2 = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles2 = {ftpFile2};
        EasyMock.expect(ftp2.listFiles("/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml")).andReturn(ftpFiles2).times(1);

        // mock step 3
        client.setResumeBroken(true);

        // mock step 4
        EasyMock.expect(ftpFile1.getSize()).andReturn(100L).times(1);
        EasyMock.expect(ftpFile2.getSize()).andReturn(100L).times(1);

        EasyMock.replay(ftp1, ftp2, ftpFile1, ftpFile2);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToServer.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(ftp1, ftp2, ftpFile1, ftpFile2);
    }

    /**
     * FTP1 file exists, FTP2 file exists, and is resume broken transfer mode, and FTP1 file size larger than FTP2 file size,
     * and file transfer success, success is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransfer010() throws Exception {

        // =================== Before  ===================
        ServerToServer client = PowerMock.createPartialMock(ServerToServer.class, "changeDir", FTPClient.class, String.class);

        // ftp1VO
        FTPFileInfoVO vo1 = AbstractFTPClient.convertFTPUrlToVO("ftp://username1:password1@192.168.19.251/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml");
        Field field1 = ServerToServer.class.getDeclaredField("ftp1VO");
        field1.setAccessible(true);
        field1.set(client, vo1);

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp1");
        field2.setAccessible(true);
        field2.set(client, ftp1);

        // ftp2VO
        FTPFileInfoVO vo2 = AbstractFTPClient.convertFTPUrlToVO("ftp://username2:password2@192.168.19.252/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml");
        Field field3 = ServerToServer.class.getDeclaredField("ftp2VO");
        field3.setAccessible(true);
        field3.set(client, vo2);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field4 = ServerToServer.class.getDeclaredField("ftp2");
        field4.setAccessible(true);
        field4.set(client, ftp2);

        // mock step 1
        FTPFile ftpFile1 = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles1 = {ftpFile1};
        EasyMock.expect(ftp1.listFiles("/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml")).andReturn(ftpFiles1).times(1);

        // mock step 2
        FTPFile ftpFile2 = PowerMock.createStrictMock(FTPFile.class);
        FTPFile[] ftpFiles2 = {ftpFile2};
        EasyMock.expect(ftp2.listFiles("/ftp2/cmdfile2/04_VOD_20110411093958_0010475562.xml")).andReturn(ftpFiles2).times(1);

        // mock step 3
        client.setResumeBroken(true);

        // mock step 4
        EasyMock.expect(ftpFile1.getSize()).andReturn(100L).times(1);
        EasyMock.expect(ftpFile2.getSize()).andReturn(99L).times(1);

        // mock step 5
        ftp1.setRestartOffset(99L);
        EasyMock.expectLastCall().times(1);
        ftp2.setRestartOffset(99L);
        EasyMock.expectLastCall().times(1);

        // mock step 6
        PowerMock.expectPrivate(client, "changeDir", ftp2, "/ftp2/cmdfile2").andReturn(true).times(1);

        // mock step 7
        EasyMock.expect(ftp1.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);
        EasyMock.expect(ftp2.setFileType(FTP.BINARY_FILE_TYPE)).andReturn(true).times(1);

        // mock step 8
        EasyMock.expect(ftp2.enterRemotePassiveMode()).andReturn(true).times(1);
        EasyMock.expect(ftp2.getPassiveHost()).andReturn("1.1.1.1").times(1);
        EasyMock.expect(ftp2.getPassivePort()).andReturn(123).times(1);
        EasyMock.expect(ftp1.enterRemoteActiveMode(InetAddress.getByName("1.1.1.1"), 123)).andReturn(true).times(1);

        // mock step 9
        EasyMock.expect(ftp1.remoteRetrieve("/ftp1/cmdfile1/04_VOD_20110411093958_0010475561.xml")).andReturn(true).times(1);
        EasyMock.expect(ftp2.remoteStore("04_VOD_20110411093958_0010475562.xml")).andReturn(true).times(1);

        // mock step 10
        EasyMock.expect(ftp1.completePendingCommand()).andReturn(true).times(1);
        EasyMock.expect(ftp2.completePendingCommand()).andReturn(true).times(1);

        PowerMock.replay(ServerToServer.class, client, ftp1, ftp2, ftpFile1, ftpFile2);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToServer.class.getDeclaredMethod("doTransfer");
        boolean result = (Boolean) method.invoke(client);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        PowerMock.verify(ServerToServer.class, client, ftp1, ftp2, ftpFile1, ftpFile2);
    }

    /**
     * When FTP Client is not connected to server, logout and disconnect are not to be executed.
     *
     * @throws Exception
     */
    @Test
    public void testLogoutAndDisconnect001() throws Exception {

        // =================== Before  ===================
        ServerToServer client = new ServerToServer();

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field1 = ServerToServer.class.getDeclaredField("ftp1");
        field1.setAccessible(true);
        field1.set(client, ftp1);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp2");
        field2.setAccessible(true);
        field2.set(client, ftp2);

        // mock step 1
        EasyMock.expect(ftp1.isConnected()).andReturn(false).times(1);

        // mock step 2
        EasyMock.expect(ftp2.isConnected()).andReturn(false).times(1);

        // mock step 3
        EasyMock.expect(ftp1.isConnected()).andReturn(false).times(1);

        // mock step 4
        EasyMock.expect(ftp2.isConnected()).andReturn(false).times(1);

        EasyMock.replay(ftp1, ftp2);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToServer.class.getDeclaredMethod("logoutAndDisconnect");
        method.invoke(client);

        // =================== Output  ===================

        // ===================  After  ===================
        EasyMock.verify(ftp1, ftp2);
    }

    /**
     * When FTP Client is connected to server, logout and disconnect are to be executed.
     *
     * @throws Exception
     */
    @Test
    public void testLogoutAndDisconnect002() throws Exception {

        // =================== Before  ===================
        ServerToServer client = new ServerToServer();

        // ftp1
        FTPClient ftp1 = PowerMock.createStrictMock(FTPClient.class);
        Field field1 = ServerToServer.class.getDeclaredField("ftp1");
        field1.setAccessible(true);
        field1.set(client, ftp1);

        // ftp2
        FTPClient ftp2 = PowerMock.createStrictMock(FTPClient.class);
        Field field2 = ServerToServer.class.getDeclaredField("ftp2");
        field2.setAccessible(true);
        field2.set(client, ftp2);

        // mock step 1
        EasyMock.expect(ftp1.isConnected()).andReturn(true).times(1);
        EasyMock.expect(ftp1.logout()).andReturn(true).times(1);

        // mock step 2
        EasyMock.expect(ftp2.isConnected()).andReturn(true).times(1);
        EasyMock.expect(ftp2.logout()).andReturn(true).times(1);

        // mock step 3
        EasyMock.expect(ftp1.isConnected()).andReturn(true).times(1);
        ftp1.disconnect();
        EasyMock.expectLastCall().times(1);

        // mock step 4
        EasyMock.expect(ftp2.isConnected()).andReturn(true).times(1);
        ftp2.disconnect();
        EasyMock.expectLastCall().times(1);

        EasyMock.replay(ftp1, ftp2);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = ServerToServer.class.getDeclaredMethod("logoutAndDisconnect");
        method.invoke(client);

        // =================== Output  ===================

        // ===================  After  ===================
        EasyMock.verify(ftp1, ftp2);
    }
}
