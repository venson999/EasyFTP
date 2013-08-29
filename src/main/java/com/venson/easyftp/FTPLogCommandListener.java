package com.venson.easyftp;

import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;
import org.slf4j.Logger;

/**
 * The FTP client command listener logger.
 *
 * @author venson
 */
public class FTPLogCommandListener implements ProtocolCommandListener {

    /**
     * The separator of system.
     */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * The logger of the FTP client.
     */
    private Logger logger = null;

    /**
     * Constructor method.
     *
     * @param logger The logger of the FTP client.
     */
    public FTPLogCommandListener(Logger logger) {
        this.logger = logger;
    }

    /**
     * Log the message when FTP command be sent.
     *
     * @param event The event of FTP client.
     */
    public void protocolCommandSent(ProtocolCommandEvent event) {
        if (logger.isDebugEnabled() && event.getMessage() != null) {
            logger.debug(event.getMessage().replace(LINE_SEPARATOR, ""));
        }
    }

    /**
     * Log the message when FTP command be received.
     *
     * @param event The event of FTP client.
     */
    public void protocolReplyReceived(ProtocolCommandEvent event) {
        if (logger.isDebugEnabled() && event.getMessage() != null) {
            logger.debug(event.getMessage().replace(LINE_SEPARATOR, ""));
        }
    }
}
