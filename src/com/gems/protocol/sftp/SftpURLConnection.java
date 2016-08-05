package com.gems.protocol.sftp;

import java.io.IOException;
import java.net.URLConnection;
import java.net.URL;

/**
 * Created by nayan on 8/5/16.
 */
public class SftpURLConnection extends URLConnection {

    protected SftpURLConnection(URL url)
    {
        super(url);
    }

    @Override
    public void connect() throws IOException {

    }
}
