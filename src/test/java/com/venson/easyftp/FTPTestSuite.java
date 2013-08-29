package com.venson.easyftp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * UtilTestSuite
 *
 * @author venson
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    AbstractFTPClientTest.class,
    ServerToClientTest.class,
    ServerToServerTest.class
})
public class FTPTestSuite {
}
