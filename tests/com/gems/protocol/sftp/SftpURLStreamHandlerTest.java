package com.gems.protocol.sftp;

import com.gems.protocol.StreamHandlerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by nayan on 8/7/16.
 */
public class SftpURLStreamHandlerTest {

    @Before
    public void setUp() throws Exception {
        URLStreamHandlerFactory factory = new MockedStreamHandlerFactory();
        try {
            URL.setURLStreamHandlerFactory(factory);
        } catch (final Error e) {

        }
    }

    @After
    public void tearDown() throws Exception {
        try {
            final Field factoryField = URL.class.getDeclaredField("factory");
            factoryField.setAccessible(true);
            factoryField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error("Could not access factory field on URL class: {}", e);
        }

    }

    @Test
    public void openConnection() throws Exception
    {
        URL url = new URL("sftp://www.foobar.com/getfile");
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        byte[] buffer = new byte[10];
        int readBytes = inputStream.read(buffer);
        assertEquals(6, readBytes);
    }
}

class MockedStreamHandlerFactory implements URLStreamHandlerFactory {
    public URLStreamHandler createURLStreamHandler(String protocol) {
        if (protocol.equals("sftp")) {
            return new MockedSftpURLStreamHandler();
        }
        return null;
    }
}

class MockedSftpURLStreamHandler extends URLStreamHandler {

    protected URLConnection openConnection(URL url) throws IOException
    {
        return new MockedSftpUrlConnection(url);
    }
}

class MockedSftpUrlConnection extends URLConnection {

    public MockedSftpUrlConnection(URL url)
    {
        super(url);
    }

    public void connect() {

    }

    public InputStream getInputStream() {
        byte[] buffer = "abcdef".getBytes();
        return new ByteArrayInputStream(buffer);
    }
}