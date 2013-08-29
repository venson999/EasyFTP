package com.venson.easyftp;

/**
 * The value object with FTP information.
 *
 * @author venson
 */
public class FTPFileInfoVO {

    /**
     * The user name of FTP server.
     */
    private String userName;

    /**
     * The password of FTP server.
     */
    private String password;

    /**
     * The IP address of FTP server.
     */
    private String ip;

    /**
     * The port of FTP server.
     */
    private String port;

    /**
     * The original URL.
     */
    private String url;

    /**
     * The full path of the file.
     */
    private String fileFullPath;

    /**
     * The directory of the file.
     */
    private String fileDirectory;

    /**
     * The file name.
     */
    private String fileName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileFullPath() {
        return fileFullPath;
    }

    public void setFileFullPath(String fileFullPath) {
        this.fileFullPath = fileFullPath;
    }

    public String getFileDirectory() {
        return fileDirectory;
    }

    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
