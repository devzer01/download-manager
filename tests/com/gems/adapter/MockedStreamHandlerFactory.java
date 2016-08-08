package com.gems.adapter;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

/**
 * mocked URLStreamHandlerFactory to be used to test various unit-tests related to sftp
 */
public class MockedStreamHandlerFactory implements URLStreamHandlerFactory {
    public URLStreamHandler createURLStreamHandler(String protocol) {
        if (protocol.equals("sftp")) {
            return new MockedSftpURLStreamHandler();
        }
        return null;
    }
}
