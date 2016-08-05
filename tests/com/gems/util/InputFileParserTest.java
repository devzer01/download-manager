package com.gems.util;

import static org.junit.Assert.*;

import com.gems.exception.InvalidInputFileException;
import org.junit.Test;
import org.junit.experimental.ParallelComputer;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by nayan on 8/5/16.
 */
public class InputFileParserTest {

    @Test
    public void testLoadNoneExistFile() {
        boolean expectedExceptionThrown = false;
        try {
            InputFileParser.load("foobar.txt");
        } catch (FileNotFoundException e) {
            expectedExceptionThrown = true;
        } catch (InvalidInputFileException e) {

        }

        assertTrue(expectedExceptionThrown);
    }

    @Test
    public void testLoadDummyFile() {
        boolean expectedExceptionThrown = false;
        ArrayList<String> resources = null;
        try {
            resources = InputFileParser.load("resources/testfile.txt");
        } catch (InvalidInputFileException e) {
            expectedExceptionThrown = true;
        } catch (FileNotFoundException e) {
            expectedExceptionThrown = true;
        }

        assertTrue(!resources.isEmpty());
    }

    @Test
    public void testResourceValidator() {
        assertTrue(InputFileParser.validateResource("http://test.com/foo"));
    }

    @Test
    public void testParser() {
        assertTrue(InputFileParser.validateResource("http://test.com/foo"));
    }
}