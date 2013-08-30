# EasyFTP

An apache commons-net FTP wrapper that make it simple, stupid, and easy to use.

## Feature

* Download and upload file.
* Transfer file server to server.
* Automatic failure retry.
* Resume broken transfer.

## Samples

Download and upload file.

```java
ServerToClient stc = new ServerToClient();

// Set transfer mode to upload. Defaults to download.
stc.setTransferMode(FTPTransferMode.UPLOAD);

// Turn on resume broken transfer mode. Defaults to off.
stc.setResumeBroken(true);

// Set the max count of retry time to 3. Defaults to 0.
stc.setRetryTimes(3);

// Set the interval wait time between retries to 10 seconds. Defaults to 1 second.
stc.setRetryWaitTime(10L);

String remote = "ftp://username:password@192.168.1.1/ftp/file/foo.xml";
String local = "/var/file/bar.xml";
if (stc.transfer(remote, local)) {
    System.out.println("Success!");
} else {
    System.out.println("Failure!");
}
```

Transfer file server to server.

```java
ServerToServer sts = new ServerToServer();

String server1 = "ftp://username:password@192.168.1.1:20/ftp/file/foo.xml";
String server2 = "ftp://username:password@192.168.1.2:30/ftp/file/bar.xml";

// Transfer file from server1 to server2.
if (sts.transfer(server1, server2)) {
    System.out.println("Success!");
} else {
    System.out.println("Failure!");
}
```

## License

EasyFTP is available under the terms of the MIT License.
