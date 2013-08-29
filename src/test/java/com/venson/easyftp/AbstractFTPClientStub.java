package com.venson.easyftp;

import java.io.IOException;

import com.venson.easyftp.AbstractFTPClient;

/**
 * The support test class of AbstractFTPClient.
 *
 * @author venson
 */
public class AbstractFTPClientStub extends AbstractFTPClient {

    private boolean connectResult;

    private boolean loginResult;

    private boolean doTransferResult;

    public void setConnectResult(boolean connectResult) {
        this.connectResult = connectResult;
    }

    public void setLoginResult(boolean loginResult) {
        this.loginResult = loginResult;
    }

    public void setDoTransferResult(boolean doTransferResult) {
        this.doTransferResult = doTransferResult;
    }

    @Override
    protected boolean connect() throws IOException {
        return connectResult;
    }

    @Override
    protected boolean login() throws IOException {
        return loginResult;
    }

    @Override
    protected void logoutAndDisconnect() {
    }

    @Override
    protected boolean doTransfer() throws IOException {
        return doTransferResult;
    }
}
