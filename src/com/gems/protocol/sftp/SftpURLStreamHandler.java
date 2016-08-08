package com.gems.protocol.sftp;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
/**
 * Created by nayan on 8/5/16.
 */
public class SftpURLStreamHandler extends URLStreamHandler {

    protected int defaultPort = 22;

    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        SftpURLConnection sftpURLConnection = new SftpURLConnection(url);
        return sftpURLConnection;
    }

    @Override
    protected int getDefaultPort()
    {
        return defaultPort;
    }
}
