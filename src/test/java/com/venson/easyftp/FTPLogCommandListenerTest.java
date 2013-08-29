package com.venson.easyftp;

import org.apache.commons.net.ProtocolCommandEvent;
import org.easymock.EasyMock;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;
import org.slf4j.Logger;

import com.venson.easyftp.FTPLogCommandListener;

/**
 * The test class of FTPLogCommandListener.
 *
 * @author venson
 */
public class FTPLogCommandListenerTest {

    /**
     * When debug log is off, expects nothing in output.
     */
    @Test
    public void testProtocolCommandSent001() {

        // =================== Before  ===================
        // mock step 1
        Logger logger = EasyMock.createStrictMock(Logger.class);
        EasyMock.expect(logger.isDebugEnabled()).andReturn(false).times(1);

        EasyMock.replay(logger);

        // ===================  Input  ===================

        // =================== Process ===================
        FTPLogCommandListener listener = new FTPLogCommandListener(logger);
        listener.protocolCommandSent(null);

        // =================== Output  ===================

        // ===================  After  ===================
        EasyMock.verify(logger);
    }

    /**
     * When debug log is on, but event message is null, expects nothing in output.
     */
    @Test
    public void testProtocolCommandSent002() {

        // =================== Before  ===================
        // mock step 1
        Logger logger = EasyMock.createStrictMock(Logger.class);
        EasyMock.expect(logger.isDebugEnabled()).andReturn(true).times(1);

        // mock step 2
        ProtocolCommandEvent event = PowerMock.createStrictMock(ProtocolCommandEvent.class);
        EasyMock.expect(event.getMessage()).andReturn(null).times(1);

        EasyMock.replay(logger, event);

        // ===================  Input  ===================

        // =================== Process ===================
        FTPLogCommandListener listener = new FTPLogCommandListener(logger);
        listener.protocolCommandSent(event);

        // =================== Output  ===================

        // ===================  After  ===================
        EasyMock.verify(logger, event);
    }

    /**
     * When debug log is on, and event message is "aaa", expects "aaa" in output.
     */
    @Test
    public void testProtocolCommandSent003() {

        // =================== Before  ===================
        // mock step 1
        Logger logger = EasyMock.createStrictMock(Logger.class);
        EasyMock.expect(logger.isDebugEnabled()).andReturn(true).times(1);

        // mock step 2
        ProtocolCommandEvent event = PowerMock.createStrictMock(ProtocolCommandEvent.class);
        EasyMock.expect(event.getMessage()).andReturn("aaa").times(1);

        // mock step 3
        EasyMock.expect(event.getMessage()).andReturn("aaa").times(1);
        logger.debug("aaa");
        EasyMock.expectLastCall().times(1);

        EasyMock.replay(logger, event);

        // ===================  Input  ===================

        // =================== Process ===================
        FTPLogCommandListener listener = new FTPLogCommandListener(logger);
        listener.protocolCommandSent(event);

        // =================== Output  ===================

        // ===================  After  ===================
        EasyMock.verify(logger, event);
    }

    /**
     * When debug log is on, and event message is "aaa" + "line.separator", expects "aaa" in output.
     */
    @Test
    public void testProtocolCommandSent004() {

        // =================== Before  ===================
        // mock step 1
        Logger logger = EasyMock.createStrictMock(Logger.class);
        EasyMock.expect(logger.isDebugEnabled()).andReturn(true).times(1);

        // mock step 2
        ProtocolCommandEvent event = PowerMock.createStrictMock(ProtocolCommandEvent.class);
        EasyMock.expect(event.getMessage()).andReturn("aaa" + System.getProperty("line.separator")).times(1);

        // mock step 3
        EasyMock.expect(event.getMessage()).andReturn("aaa" + System.getProperty("line.separator")).times(1);
        logger.debug("aaa");
        EasyMock.expectLastCall().times(1);

        EasyMock.replay(logger, event);

        // ===================  Input  ===================

        // =================== Process ===================
        FTPLogCommandListener listener = new FTPLogCommandListener(logger);
        listener.protocolCommandSent(event);

        // =================== Output  ===================

        // ===================  After  ===================
        EasyMock.verify(logger, event);
    }

    /**
     * When debug log is off, expects nothing in output.
     */
    @Test
    public void testProtocolReplyReceived001() {

        // =================== Before  ===================
        // mock step 1
        Logger logger = EasyMock.createStrictMock(Logger.class);
        EasyMock.expect(logger.isDebugEnabled()).andReturn(false).times(1);

        EasyMock.replay(logger);

        // ===================  Input  ===================

        // =================== Process ===================
        FTPLogCommandListener listener = new FTPLogCommandListener(logger);
        listener.protocolReplyReceived(null);

        // =================== Output  ===================

        // ===================  After  ===================
        EasyMock.verify(logger);
    }

    /**
     * When debug log is on, but event message is null, expects nothing in output.
     */
    @Test
    public void testProtocolReplyReceived002() {

        // =================== Before  ===================
        // mock step 1
        Logger logger = EasyMock.createStrictMock(Logger.class);
        EasyMock.expect(logger.isDebugEnabled()).andReturn(true).times(1);

        // mock step 2
        ProtocolCommandEvent event = PowerMock.createStrictMock(ProtocolCommandEvent.class);
        EasyMock.expect(event.getMessage()).andReturn(null).times(1);

        EasyMock.replay(logger, event);

        // ===================  Input  ===================

        // =================== Process ===================
        FTPLogCommandListener listener = new FTPLogCommandListener(logger);
        listener.protocolReplyReceived(event);

        // =================== Output  ===================

        // ===================  After  ===================
        EasyMock.verify(logger, event);
    }

    /**
     * When debug log is on, and event message is "aaa", expects "aaa" in output.
     */
    @Test
    public void testProtocolReplyReceived003() {

        // =================== Before  ===================
        // mock step 1
        Logger logger = EasyMock.createStrictMock(Logger.class);
        EasyMock.expect(logger.isDebugEnabled()).andReturn(true).times(1);

        // mock step 2
        ProtocolCommandEvent event = PowerMock.createStrictMock(ProtocolCommandEvent.class);
        EasyMock.expect(event.getMessage()).andReturn("aaa").times(1);

        // mock step 3
        EasyMock.expect(event.getMessage()).andReturn("aaa").times(1);
        logger.debug("aaa");
        EasyMock.expectLastCall().times(1);

        EasyMock.replay(logger, event);

        // ===================  Input  ===================

        // =================== Process ===================
        FTPLogCommandListener listener = new FTPLogCommandListener(logger);
        listener.protocolReplyReceived(event);

        // =================== Output  ===================

        // ===================  After  ===================
        EasyMock.verify(logger, event);
    }

    /**
     * When debug log is on, and event message is "aaa" + "line.separator", expects "aaa" in output.
     */
    @Test
    public void testProtocolReplyReceived004() {

        // =================== Before  ===================
        // mock step 1
        Logger logger = EasyMock.createStrictMock(Logger.class);
        EasyMock.expect(logger.isDebugEnabled()).andReturn(true).times(1);

        // mock step 2
        ProtocolCommandEvent event = PowerMock.createStrictMock(ProtocolCommandEvent.class);
        EasyMock.expect(event.getMessage()).andReturn("aaa" + System.getProperty("line.separator")).times(1);

        // mock step 3
        EasyMock.expect(event.getMessage()).andReturn("aaa" + System.getProperty("line.separator")).times(1);
        logger.debug("aaa");
        EasyMock.expectLastCall().times(1);

        EasyMock.replay(logger, event);

        // ===================  Input  ===================

        // =================== Process ===================
        FTPLogCommandListener listener = new FTPLogCommandListener(logger);
        listener.protocolReplyReceived(event);

        // =================== Output  ===================

        // ===================  After  ===================
        EasyMock.verify(logger, event);
    }
}
