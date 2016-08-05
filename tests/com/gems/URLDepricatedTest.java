package com.gems;

import com.gems.exception.InvalidUrlException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nayana on 8/5/16.
 */
public class URLDepricatedTest {
    @Test
    public void getScheme() throws InvalidUrlException {
        URLDepricated urlDepricated = new URLDepricated("http://www.foobar.com/foobar");
        assertEquals("http", urlDepricated.getScheme());

        boolean exceptionThrown = false;

        try {
            urlDepricated = new URLDepricated("http//www.foobar.com/foobar");
            assertEquals("http", urlDepricated.getScheme());
        } catch (InvalidUrlException e) {
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);
    }

}