package com.gems.adapter;

import com.gems.protocol.sftp.DisconnectStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by nayan on 8/8/16.
 */
public class MockedSftpUrlConnection extends URLConnection implements DisconnectStream {

    public MockedSftpUrlConnection(URL url)
    {
        super(url);
    }

    public void connect() {
        //TODO add spycode?
    }

    public void disconnect() {
        //TODO add spycode?
    }

    public InputStream getInputStream() throws IOException {

        if (url.getFile().equals("/foobar")) {
            byte[] buffer = "abcdef".getBytes();
            return new ByteArrayInputStream(buffer);
        }

        throw new IOException();
    }
}
