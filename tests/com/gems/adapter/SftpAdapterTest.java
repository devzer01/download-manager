package com.gems.adapter;

import com.gems.ClearFactory;
import com.gems.model.Progress;
import com.gems.model.Status;
import com.gems.model.Task;
import com.gems.protocol.sftp.DisconnectStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import static org.junit.Assert.*;

/**
 * Created by nayana on 8/8/16.
 */
public class SftpAdapterTest {

    protected long expectedFileSize = 6;

    /**
     * using mocked URLConnections
     * file download success
     * @throws Exception
     */
    @Test
    public void download() throws Exception {
        Task task = new Task(new URL("sftp://www.foobar.com/foobar"), new Progress(Status.INIT));
        SftpAdapter sftpAdapter = new SftpAdapter(task);
        File file = sftpAdapter.download();
        assertEquals(expectedFileSize, file.length());
    }

    /**
     * filedownload fails
     * @throws Exception
     */
    @Test
    public void downloadFail() throws Exception {
        Task task = new Task(new URL("sftp://www.foobar.com/failedfile"), new Progress(Status.INIT));
        SftpAdapter sftpAdapter = new SftpAdapter(task);
        File file = sftpAdapter.download();
        assertEquals(Status.ERROR, task.getProgress().status);
    }

    /**
     * we are setting our MockedUrlStreamHandler factory
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        URLStreamHandlerFactory factory = new MockedStreamHandlerFactory();
        try {
            URL.setURLStreamHandlerFactory(factory);
        } catch (final Error e) {

        }
    }

    /**
     * using reflection we will unset the URLStreamHandler factory
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        ClearFactory.clearFactory();

    }

    /**
     * Redundent Test testing successful buffer read
     * @throws Exception
     */
    @Test
    public void openConnection() throws Exception
    {
        URL url = new URL("sftp://www.foobar.com/foobar");
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        byte[] buffer = new byte[10];
        int readBytes = inputStream.read(buffer);
        inputStream.close();
        assertEquals(expectedFileSize, readBytes);
    }
}

