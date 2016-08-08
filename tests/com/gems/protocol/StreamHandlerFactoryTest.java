package com.gems.protocol;

import com.gems.protocol.sftp.SftpURLConnection;
import com.gems.protocol.sftp.SftpURLStreamHandler;
import org.junit.Test;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.IOException;
import java.net.*;

import static org.junit.Assert.*;

/**
 * Created by nayana on 8/8/16.
 */
public class StreamHandlerFactoryTest {
    @Test
    public void createURLStreamHandler() throws Exception {
        StreamHandlerFactory factory = new StreamHandlerFactory();
        URLStreamHandler urlStreamHandler = factory.createURLStreamHandler("sftp");
        assertTrue(urlStreamHandler instanceof SftpURLStreamHandler);
    }

}