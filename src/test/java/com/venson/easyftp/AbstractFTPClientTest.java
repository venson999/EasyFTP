package com.venson.easyftp;

import java.lang.reflect.Method;

import junit.framework.Assert;

import org.apache.commons.net.ftp.FTPClient;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.venson.easyftp.AbstractFTPClient;
import com.venson.easyftp.FTPFileInfoVO;

/**
 * The test class of AbstractFTPClient.
 *
 * @author venson
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ FTPClient.class })
public class AbstractFTPClientTest {

    /**
     * When input URL is null, NullException is expected.
     */
    @Test
    public void testConvertFTPUrlToVO001() {

        // =================== Before  ===================

        // ===================  Input  ===================
        String url = null;

        // =================== Process ===================
        Exception result = null;
        FTPFileInfoVO resultVO = null;
        try {
            resultVO = AbstractFTPClient.convertFTPUrlToVO(url);
        } catch (Exception e) {
            result = e;
        }

        // =================== Output  ===================
        Assert.assertEquals(true, result instanceof NullPointerException);
        Assert.assertEquals(null, resultVO);

        // ===================  After  ===================
    }

    /**
     * When input URL is empty string, IllegalArgumentException is expected.
     */
    @Test
    public void testConvertFTPUrlToVO002() {

        // =================== Before  ===================

        // ===================  Input  ===================
        String url = "";

        // =================== Process ===================
        Exception result = null;
        FTPFileInfoVO resultVO = null;
        try {
            resultVO = AbstractFTPClient.convertFTPUrlToVO(url);
        } catch (Exception e) {
            result = e;
        }

        // =================== Output  ===================
        Assert.assertEquals(true, result instanceof IllegalArgumentException);
        Assert.assertEquals(null, resultVO);

        // ===================  After  ===================
    }

    /**
     * When input URL format is invalid, IllegalArgumentException is expected.
     */
    @Test
    public void testConvertFTPUrlToVO003() {

        // =================== Before  ===================

        // ===================  Input  ===================
        String url = "AAA";

        // =================== Process ===================
        Exception result = null;
        FTPFileInfoVO resultVO = null;
        try {
            resultVO = AbstractFTPClient.convertFTPUrlToVO(url);
        } catch (Exception e) {
            result = e;
        }

        // =================== Output  ===================
        Assert.assertEquals(true, result instanceof IllegalArgumentException);
        Assert.assertEquals(null, resultVO);

        // ===================  After  ===================
    }

    /**
     * When input URL is valid, and port is not specified, success is expected.
     */
    @Test
    public void testConvertFTPUrlToVO004() {

        // =================== Before  ===================

        // ===================  Input  ===================
        String url = "ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml";

        // =================== Process ===================
        FTPFileInfoVO result = AbstractFTPClient.convertFTPUrlToVO(url);

        // =================== Output  ===================
        Assert.assertEquals("username", result.getUserName());
        Assert.assertEquals("password", result.getPassword());
        Assert.assertEquals("192.168.19.251", result.getIp());
        Assert.assertEquals(null, result.getPort());
        Assert.assertEquals("ftp://username:password@192.168.19.251/ftp/cmdfile/04_VOD_20110411093958_001047556.xml", result.getUrl());
        Assert.assertEquals("/ftp/cmdfile/04_VOD_20110411093958_001047556.xml", result.getFileFullPath());
        Assert.assertEquals("/ftp/cmdfile", result.getFileDirectory());
        Assert.assertEquals("04_VOD_20110411093958_001047556.xml", result.getFileName());

        // ===================  After  ===================
    }

    /**
     * When input URL is valid, and file name is specified, and parent directory is not specified,
     * success is expected.
     */
    @Test
    public void testConvertFTPUrlToVO005() {

        // =================== Before  ===================

        // ===================  Input  ===================
        String url = "ftp://username:password@192.168.19.251:20/04_VOD_20110411093958_001047556.xml";

        // =================== Process ===================
        FTPFileInfoVO result = AbstractFTPClient.convertFTPUrlToVO(url);

        // =================== Output  ===================
        Assert.assertEquals(null, result.getFileDirectory());
        Assert.assertEquals("/04_VOD_20110411093958_001047556.xml", result.getFileName());
        Assert.assertEquals("/04_VOD_20110411093958_001047556.xml", result.getFileFullPath());

        // ===================  After  ===================
    }

    /**
     * When input URL valid, and port is specified, success is expected.
     */
    @Test
    public void testConvertFTPUrlToVO006() {

        // =================== Before  ===================

        // ===================  Input  ===================
        String url = "ftp://username:password@192.168.19.251:20/ftp/cmdfile/04_VOD_20110411093958_001047556.xml";

        // =================== Process ===================
        FTPFileInfoVO result = AbstractFTPClient.convertFTPUrlToVO(url);

        // =================== Output  ===================
        Assert.assertEquals("20", result.getPort());

        // ===================  After  ===================
    }

    /**
     * When input URL is not begin with "ftp://", IllegalArgumentException is expected.
     */
    @Test
    public void testConvertFTPUrlToVO007() {

        // =================== Before  ===================

        // ===================  Input  ===================
        String url = "aftp://username:password@192.168.19.251/ftp/cmdfile/";

        // =================== Process ===================
        Exception result = null;
        FTPFileInfoVO resultVO = null;
        try {
            resultVO = AbstractFTPClient.convertFTPUrlToVO(url);
        } catch (Exception e) {
            result = e;
        }

        // =================== Output  ===================
        Assert.assertEquals(true, result instanceof IllegalArgumentException);
        Assert.assertEquals(null, resultVO);

        // ===================  After  ===================
    }

    /**
     * When input URL is end with "/",  IllegalArgumentException is expected.
     */
    @Test
    public void testConvertFTPUrlToVO008() {

        // =================== Before  ===================

        // ===================  Input  ===================
        String url = "ftp://username:password@192.168.19.251/ftp/cmdfile/";

        // =================== Process ===================
        Exception result = null;
        FTPFileInfoVO resultVO = null;
        try {
            resultVO = AbstractFTPClient.convertFTPUrlToVO(url);
        } catch (Exception e) {
            result = e;
        }

        // =================== Output  ===================
        Assert.assertEquals(true, result instanceof IllegalArgumentException);
        Assert.assertEquals(null, resultVO);

        // ===================  After  ===================
    }

    /**
     * When input URL only has "/", IllegalArgumentException is expected.
     */
    @Test
    public void testConvertFTPUrlToVO009() {

        // =================== Before  ===================

        // ===================  Input  ===================
        String url = "ftp://username:password@192.168.19.251/";

        // =================== Process ===================
        Exception result = null;
        FTPFileInfoVO resultVO = null;
        try {
            resultVO = AbstractFTPClient.convertFTPUrlToVO(url);
        } catch (Exception e) {
            result = e;
        }

        // =================== Output  ===================
        Assert.assertEquals(true, result instanceof IllegalArgumentException);
        Assert.assertEquals(null, resultVO);

        // ===================  After  ===================
    }

    /**
     * Test default behaviors.
     */
    @Test
    public void testDefaultBehavior001() {

        // =================== Before  ===================

        // ===================  Input  ===================

        // =================== Process ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();

        // =================== Output  ===================
        Assert.assertEquals(0, stub.getRetryTimes());
        Assert.assertEquals(0, stub.getCurrentRetryTimes());
        Assert.assertEquals(1L, stub.getRetryWaitTime());
        Assert.assertEquals(true, stub.isResumeBroken());

        // ===================  After  ===================
    }

    /**
     * Test changing default behaviors.
     */
    @Test
    public void testChangeBehavior001() {

        // =================== Before  ===================

        // ===================  Input  ===================

        // =================== Process ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();
        stub.setRetryTimes(1);
        stub.setRetryWaitTime(2L);
        stub.setResumeBroken(false);

        // =================== Output  ===================
        Assert.assertEquals(1, stub.getRetryTimes());
        Assert.assertEquals(0, stub.getCurrentRetryTimes());
        Assert.assertEquals(2L, stub.getRetryWaitTime());
        Assert.assertEquals(false, stub.isResumeBroken());

        // ===================  After  ===================
    }

    /**
     * When connect to FTP server failed, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferFlow001() throws Exception {

        // =================== Before  ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();
        stub.setConnectResult(false);
        stub.setLoginResult(true);
        stub.setDoTransferResult(true);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = AbstractFTPClient.class.getDeclaredMethod("doTransferFlow");
        boolean result = (Boolean) method.invoke(stub);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
    }

    /**
     * When connect to FTP server succeed, but login to FTP server failed, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferFlow002() throws Exception {

        // =================== Before  ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();
        stub.setConnectResult(true);
        stub.setLoginResult(false);
        stub.setDoTransferResult(true);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = AbstractFTPClient.class.getDeclaredMethod("doTransferFlow");
        boolean result = (Boolean) method.invoke(stub);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
    }

    /**
     * When connect and login to FTP server succeed, but file transfer failed,
     * expect the count of retry time is 0.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferFlow003() throws Exception {

        // =================== Before  ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();
        stub.setConnectResult(true);
        stub.setLoginResult(true);
        stub.setDoTransferResult(false);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = AbstractFTPClient.class.getDeclaredMethod("doTransferFlow");
        boolean result = (Boolean) method.invoke(stub);

        // =================== Output  ===================
        Assert.assertEquals(false, result);
        Assert.assertEquals(0, stub.getCurrentRetryTimes());

        // ===================  After  ===================
    }

    /**
     * When the max count of retry time is set to 1, connect and login to FTP server succeed,
     * but file transfer failed, expect the count of retry time is 1.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferFlow004() throws Exception {

        // =================== Before  ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();
        stub.setConnectResult(true);
        stub.setLoginResult(true);
        stub.setDoTransferResult(false);
        stub.setRetryTimes(1);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = AbstractFTPClient.class.getDeclaredMethod("doTransferFlow");
        boolean result = (Boolean) method.invoke(stub);

        // =================== Output  ===================
        Assert.assertEquals(false, result);
        Assert.assertEquals(1, stub.getCurrentRetryTimes());

        // ===================  After  ===================
    }

    /**
     * When connect and login to FTP server succeed, and file transfer succeed,
     * expect the count of retry time is 0.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferFlow005() throws Exception {

        // =================== Before  ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();
        stub.setConnectResult(true);
        stub.setLoginResult(true);
        stub.setDoTransferResult(true);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = AbstractFTPClient.class.getDeclaredMethod("doTransferFlow");
        boolean result = (Boolean) method.invoke(stub);

        // =================== Output  ===================
        Assert.assertEquals(true, result);
        Assert.assertEquals(0, stub.getCurrentRetryTimes());

        // ===================  After  ===================
    }

    /**
     * When the max count of retry time is set to 1, connect and login to FTP server succeed,
     * and file transfer succeed, expect the count of retry time is 0.
     *
     * @throws Exception
     */
    @Test
    public void testDoTransferFlow006() throws Exception {

        // =================== Before  ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();
        stub.setConnectResult(true);
        stub.setLoginResult(true);
        stub.setDoTransferResult(true);
        stub.setRetryTimes(1);

        // ===================  Input  ===================

        // =================== Process ===================
        Method method = AbstractFTPClient.class.getDeclaredMethod("doTransferFlow");
        boolean result = (Boolean) method.invoke(stub);

        // =================== Output  ===================
        Assert.assertEquals(true, result);
        Assert.assertEquals(0, stub.getCurrentRetryTimes());

        // ===================  After  ===================
    }

    /**
     * When FTP port is not specified, success is expected.
     *
     * @throws Exception
     */
    @Test
    public void testConnect001() throws Exception {

        // =================== Before  ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();

        FTPClient mock = PowerMock.createStrictMock(FTPClient.class);
        mock.connect("192.168.19.251");
        EasyMock.expectLastCall().times(1);
        EasyMock.expect(mock.getRemotePort()).andReturn(200).anyTimes();
        EasyMock.expect(mock.getReplyCode()).andReturn(200).times(1);
        EasyMock.replay(mock);

        // ===================  Input  ===================
        FTPFileInfoVO vo = new FTPFileInfoVO();
        vo.setIp("192.168.19.251");
        vo.setPort(null);

        // =================== Process ===================
        Method method = AbstractFTPClient.class.getDeclaredMethod("connect", FTPClient.class, FTPFileInfoVO.class);
        boolean result = (Boolean) method.invoke(stub, mock, vo);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        EasyMock.verify(mock);
    }

    /**
     * When FTP port is empty string, success is expected.
     *
     *
     * @throws Exception
     */
    @Test
    public void testConnect002() throws Exception {

        // =================== Before  ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();

        FTPClient mock = PowerMock.createStrictMock(FTPClient.class);
        mock.connect("192.168.19.251");
        EasyMock.expectLastCall().times(1);
        EasyMock.expect(mock.getRemotePort()).andReturn(200).anyTimes();
        EasyMock.expect(mock.getReplyCode()).andReturn(200).times(1);
        EasyMock.replay(mock);

        // ===================  Input  ===================
        FTPFileInfoVO vo = new FTPFileInfoVO();
        vo.setIp("192.168.19.251");
        vo.setPort("");

        // =================== Process ===================
        Method method = AbstractFTPClient.class.getDeclaredMethod("connect", FTPClient.class, FTPFileInfoVO.class);
        boolean result = (Boolean) method.invoke(stub, mock, vo);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        EasyMock.verify(mock);
    }

    /**
     * When FTP port is specified, success is expected.
     *
     * @throws Exception
     */
    @Test
    public void testConnect003() throws Exception {

        // =================== Before  ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();

        FTPClient mock = PowerMock.createStrictMock(FTPClient.class);
        mock.connect("192.168.19.251", 20);
        EasyMock.expectLastCall().times(1);
        EasyMock.expect(mock.getRemotePort()).andReturn(200).anyTimes();
        EasyMock.expect(mock.getReplyCode()).andReturn(200).times(1);
        EasyMock.replay(mock);

        // ===================  Input  ===================
        FTPFileInfoVO vo = new FTPFileInfoVO();
        vo.setIp("192.168.19.251");
        vo.setPort("20");

        // =================== Process ===================
        Method method = AbstractFTPClient.class.getDeclaredMethod("connect", FTPClient.class, FTPFileInfoVO.class);
        boolean result = (Boolean) method.invoke(stub, mock, vo);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        EasyMock.verify(mock);
    }

    /**
     * When connection is something wrong, failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testConnect004() throws Exception {

        // =================== Before  ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();

        FTPClient mock = PowerMock.createStrictMock(FTPClient.class);
        mock.connect("192.168.19.251", 20);
        EasyMock.expectLastCall().times(1);
        EasyMock.expect(mock.getRemotePort()).andReturn(200).anyTimes();
        EasyMock.expect(mock.getReplyCode()).andReturn(100).times(1);
        EasyMock.replay(mock);

        // ===================  Input  ===================
        FTPFileInfoVO vo = new FTPFileInfoVO();
        vo.setIp("192.168.19.251");
        vo.setPort("20");

        // =================== Process ===================
        Method method = AbstractFTPClient.class.getDeclaredMethod("connect", FTPClient.class, FTPFileInfoVO.class);
        boolean result = (Boolean) method.invoke(stub, mock, vo);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(mock);
    }

    /**
     * When input path is not specified, success is expected.
     *
     * @throws Exception
     */
    @Test
    public void testChangeDir001() throws Exception {

        // =================== Before  ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();

        FTPClient mock = PowerMock.createStrictMock(FTPClient.class);
        EasyMock.replay(mock);

        // ===================  Input  ===================
        String path = null;

        // =================== Process ===================
        Method method = AbstractFTPClient.class.getDeclaredMethod("changeDir", FTPClient.class, String.class);
        boolean result = (Boolean) method.invoke(stub, mock, path);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        EasyMock.verify(mock);
    }

    /**
     * When input path is empty string, success is expected.
     *
     * @throws Exception
     */
    @Test
    public void testChangeDir002() throws Exception {

        // =================== Before  ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();

        FTPClient mock = PowerMock.createStrictMock(FTPClient.class);

        EasyMock.replay(mock);

        // ===================  Input  ===================
        String path = "";

        // =================== Process ===================
        Method method = AbstractFTPClient.class.getDeclaredMethod("changeDir", FTPClient.class, String.class);
        boolean result = (Boolean) method.invoke(stub, mock, path);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        EasyMock.verify(mock);
    }

    /**
     * When input path is "/path1" which really exists, success is expected.
     *
     * @throws Exception
     */
    @Test
    public void testChangeDir003() throws Exception {

        // =================== Before  ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();

        FTPClient mock = PowerMock.createStrictMock(FTPClient.class);

        // mock step 1
        EasyMock.expect(mock.changeWorkingDirectory("/")).andReturn(true).times(1);
        EasyMock.expect(mock.changeWorkingDirectory("path1")).andReturn(true).times(1);

        EasyMock.replay(mock);

        // ===================  Input  ===================
        String path = "/path1";

        // =================== Process ===================
        Method method = AbstractFTPClient.class.getDeclaredMethod("changeDir", FTPClient.class, String.class);
        boolean result = (Boolean) method.invoke(stub, mock, path);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        EasyMock.verify(mock);
    }

    /**
     * When input path is "/path1" which not exists, and path creating and path changing are OK,
     * success is expected.
     *
     * @throws Exception
     */
    @Test
    public void testChangeDir004() throws Exception {

        // =================== Before  ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();

        FTPClient mock = PowerMock.createStrictMock(FTPClient.class);

        // mock step 1
        EasyMock.expect(mock.changeWorkingDirectory("/")).andReturn(true).times(1);
        EasyMock.expect(mock.changeWorkingDirectory("path1")).andReturn(false).times(1);

        // mock step 2
        EasyMock.expect(mock.makeDirectory("path1")).andReturn(true).times(1);
        EasyMock.expect(mock.changeWorkingDirectory("path1")).andReturn(true).times(1);

        EasyMock.replay(mock);

        // ===================  Input  ===================
        String path = "/path1";

        // =================== Process ===================
        Method method = AbstractFTPClient.class.getDeclaredMethod("changeDir", FTPClient.class, String.class);
        boolean result = (Boolean) method.invoke(stub, mock, path);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        EasyMock.verify(mock);
    }

    /**
     * When input path is "/path1" which not exists, and something wrong with path creating,
     * failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testChangeDir005() throws Exception {

        // =================== Before  ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();

        FTPClient mock = PowerMock.createStrictMock(FTPClient.class);

        // mock step 1
        EasyMock.expect(mock.changeWorkingDirectory("/")).andReturn(true).times(1);
        EasyMock.expect(mock.changeWorkingDirectory("path1")).andReturn(false).times(1);

        // mock step 2
        EasyMock.expect(mock.makeDirectory("path1")).andReturn(false).times(1);

        EasyMock.replay(mock);

        // ===================  Input  ===================
        String path = "/path1";

        // =================== Process ===================
        Method method = AbstractFTPClient.class.getDeclaredMethod("changeDir", FTPClient.class, String.class);
        boolean result = (Boolean) method.invoke(stub, mock, path);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(mock);
    }

    /**
     * When input path is "/path1" which not exists, path creating is OK, but something wrong with path changing,
     * failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testChangeDir006() throws Exception {

        // =================== Before  ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();

        FTPClient mock = PowerMock.createStrictMock(FTPClient.class);

        // mock step 1
        EasyMock.expect(mock.changeWorkingDirectory("/")).andReturn(true).times(1);
        EasyMock.expect(mock.changeWorkingDirectory("path1")).andReturn(false).times(1);

        // mock step 2
        EasyMock.expect(mock.makeDirectory("path1")).andReturn(true).times(1);
        EasyMock.expect(mock.changeWorkingDirectory("path1")).andReturn(false).times(1);

        EasyMock.replay(mock);

        // ===================  Input  ===================
        String path = "/path1";

        // =================== Process ===================
        Method method = AbstractFTPClient.class.getDeclaredMethod("changeDir", FTPClient.class, String.class);
        boolean result = (Boolean) method.invoke(stub, mock, path);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(mock);
    }

    /**
     * When input path is "/path1/path2/path3", and "/path1" exists, path creating and path changing are OK,
     * success is expected.
     *
     * @throws Exception
     */
    @Test
    public void testChangeDir007() throws Exception {

        // =================== Before  ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();

        FTPClient mock = PowerMock.createStrictMock(FTPClient.class);

        // mock step 1
        EasyMock.expect(mock.changeWorkingDirectory("/")).andReturn(true).times(1);
        EasyMock.expect(mock.changeWorkingDirectory("path1")).andReturn(true).times(1);

        // mock step 2
        EasyMock.expect(mock.changeWorkingDirectory("path2")).andReturn(false).times(1);
        EasyMock.expect(mock.makeDirectory("path2")).andReturn(true).times(1);
        EasyMock.expect(mock.changeWorkingDirectory("path2")).andReturn(true).times(1);

        // mock step 3
        EasyMock.expect(mock.changeWorkingDirectory("path3")).andReturn(false).times(1);
        EasyMock.expect(mock.makeDirectory("path3")).andReturn(true).times(1);
        EasyMock.expect(mock.changeWorkingDirectory("path3")).andReturn(true).times(1);

        EasyMock.replay(mock);

        // ===================  Input  ===================
        String path = "/path1/path2/path3";

        // =================== Process ===================
        Method method = AbstractFTPClient.class.getDeclaredMethod("changeDir", FTPClient.class, String.class);
        boolean result = (Boolean) method.invoke(stub, mock, path);

        // =================== Output  ===================
        Assert.assertEquals(true, result);

        // ===================  After  ===================
        EasyMock.verify(mock);
    }

    /**
     * When input path is "/path1/path2/path3", but something wrong with path changing to root,
     * failure is expected.
     *
     * @throws Exception
     */
    @Test
    public void testChangeDir008() throws Exception {

        // =================== Before  ===================
        AbstractFTPClientStub stub = new AbstractFTPClientStub();

        FTPClient mock = PowerMock.createStrictMock(FTPClient.class);

        // mock step 1
        EasyMock.expect(mock.changeWorkingDirectory("/")).andReturn(false).times(1);

        EasyMock.replay(mock);

        // ===================  Input  ===================
        String path = "/path1/path2/path3";

        // =================== Process ===================
        Method method = AbstractFTPClient.class.getDeclaredMethod("changeDir", FTPClient.class, String.class);
        boolean result = (Boolean) method.invoke(stub, mock, path);

        // =================== Output  ===================
        Assert.assertEquals(false, result);

        // ===================  After  ===================
        EasyMock.verify(mock);
    }
}
