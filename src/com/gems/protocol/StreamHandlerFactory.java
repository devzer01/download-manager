package com.gems.protocol;

import com.gems.protocol.sftp.SftpURLStreamHandler;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
/**
 * Created by nayan on 8/5/16.
 */
public class StreamHandlerFactory implements URLStreamHandlerFactory {

    public URLStreamHandler createURLStreamHandler(String protocol) {
        if (protocol.equals("sftp")) {
            return new SftpURLStreamHandler();
        }
        return null;
    }
}
