package com.gems.util;

import static org.junit.Assert.*;

import com.gems.exception.InvalidInputFileException;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by nayan on 8/5/16.
 */
public class InputFileParserTest {

    @Test
    public void testLoadNoneExistFile() {
        boolean expectedExceptionThrown = false;
        ArrayList<String> resources = null;
        try {
            Method method = Class.forName("com.gems.util.InputFile").getDeclaredMethod("load", String.class);
            method.setAccessible(true);
            resources = (ArrayList<String>) method.invoke(null, "resources/foo.txt");
        } catch (ClassNotFoundException x) {

        } catch (IllegalAccessException x) {

        } catch (NoSuchMethodException e) {

        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof FileNotFoundException) {
                expectedExceptionThrown = true;
            }
        }

        assertTrue(expectedExceptionThrown);
    }

    @Test
    public void testLoadDummyFile() {
        boolean expectedExceptionThrown = false;
        ArrayList<String> resources = null;
        try {
            Method method = Class.forName("com.gems.util.InputFile").getDeclaredMethod("load", String.class);
            method.setAccessible(true);
            resources = (ArrayList<String>) method.invoke(null, "resources/testfile.txt");
        } catch (ClassNotFoundException x) {

        } catch (IllegalAccessException x) {

        } catch (NoSuchMethodException e) {

        } catch (InvocationTargetException e) {

        }

        assertTrue(!resources.isEmpty());
        assertTrue(resources.size() == 3);
    }

    @Test
    public void testParser() throws FileNotFoundException, InvalidInputFileException {
        DownloadList downloadList = InputFile.getURLList("resources/testfile.txt");
        assertTrue(downloadList.size() == 2); //make sure the duplicate is removed
    }
}