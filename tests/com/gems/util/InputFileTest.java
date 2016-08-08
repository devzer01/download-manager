package com.gems.util;

import com.gems.ClearFactory;
import com.gems.exception.InvalidInputFileException;
import com.gems.model.DownloadList;
import com.gems.protocol.StreamHandlerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Field;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by nayan on 8/7/16.
 */
public class InputFileTest {

    @Before
    public void setUp() throws Exception {
        URLStreamHandlerFactory factory = new StreamHandlerFactory();
        try {
            URL.setURLStreamHandlerFactory(factory);
        } catch (final Error e) {

        }
    }

    @After
    public void tearDown() throws Exception {
        ClearFactory.clearFactory();
    }

    @Test
    public void getURLList() throws Exception {

    }

    @Test
    public void load() {
        InputFileStub inputFileStub = new InputFileStub();
        boolean expectedExceptionThrown = false;
        try {
            inputFileStub.testLoad("resource/test/url.list");
        } catch (InvalidInputFileException e) {
            expectedExceptionThrown = true;
        }

        assertTrue(expectedExceptionThrown);
    }

    @Test
    public void loadMalformedUrls() {
        InputFileStub inputFileStub = new InputFileStub();
        boolean expectedExceptionThrown = false;
        try {
            inputFileStub.testLoad("resource/malformedurls.txt");
        } catch (InvalidInputFileException e) {
            expectedExceptionThrown = true;
        }

        assertTrue(expectedExceptionThrown);
    }

    @Test
    public void testLoadDummyFile() {
        boolean expectedExceptionThrown = false;
        InputFileStub inputFileStub = new InputFileStub();
        ArrayList<String> resources = null;
        try {
            resources = inputFileStub.testLoad("resources/testfile.txt");
        } catch (InvalidInputFileException e) {

        }

        assertTrue(!resources.isEmpty());
        assertTrue(resources.size() == 4);
    }

    @Test
    public void testParser() throws FileNotFoundException, InvalidInputFileException {
        DownloadList downloadList = InputFile.getURLList("resources/testfile.txt");
        assertTrue(downloadList.size() == 3); //make sure the duplicate is removed
    }

}

class InputFileStub extends InputFile {
    public ArrayList<String> testLoad(String filename) throws InvalidInputFileException {
        return InputFileStub.load(filename);
    }
}