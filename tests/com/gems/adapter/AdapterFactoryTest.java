package com.gems.adapter;

import com.gems.ClearFactory;
import com.gems.exception.AdapterNotFoundException;
import com.gems.model.Progress;
import com.gems.model.Status;
import com.gems.model.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.net.URL;
import java.net.URLStreamHandlerFactory;

import static org.junit.Assert.*;

/**
 * Created by nayan on 8/7/16.
 */
public class AdapterFactoryTest {

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

    @Test
    public void getAdapter() throws Exception, AdapterNotFoundException {
        Task task = new Task(new URL("sftp://www.google.com/foobar"), new Progress(Status.INIT));
        Adapter adapter = AdapterFactory.getAdapter(task);
        assertTrue(adapter instanceof SftpAdapter);
    }

    @Test
    public void getAdapterNotFoundException() throws Exception
    {
        boolean exceptionThrown = false;
        Task task = new Task(new URL("file://www.google.com/foobar"), new Progress(Status.INIT));
        Adapter adapter = null;
        try {
            adapter = AdapterFactory.getAdapter(task);
        } catch (AdapterNotFoundException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }
}