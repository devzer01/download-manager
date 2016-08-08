package com.gems.adapter;

import com.gems.exception.AdapterNotFoundException;
import com.gems.model.Progress;
import com.gems.model.Status;
import com.gems.model.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by nayan on 8/7/16.
 */
public class AdapterFactoryTest {
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