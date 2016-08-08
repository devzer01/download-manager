package com.gems.adapter;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * Created by nayan on 8/8/16.
 */
public class MockedSftpURLStreamHandler extends URLStreamHandler {

    protected URLConnection openConnection(URL url) throws IOException
    {
        return new MockedSftpUrlConnection(url);
    }
}
