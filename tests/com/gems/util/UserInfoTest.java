package com.gems.util;

import com.gems.exception.AuthenticationNotFoundException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nayan on 8/9/16.
 */
public class UserInfoTest {
    @Test
    public void getUsername() throws Exception, AuthenticationNotFoundException {
        UserInfo userInfo = new UserInfo("foo:bar");
        assertEquals("foo", userInfo.getUsername());
        assertEquals("bar", userInfo.getPassword());
    }

    @Test
    public void throwsExceptionWhenNoSeperator() throws Exception {
        boolean exceptionThrown = false;
        try {
            UserInfo userInfo = new UserInfo("foo");
        } catch (AuthenticationNotFoundException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

    }

    @Test
    public void throwsExceptionWhenNoPassword() throws Exception {
        boolean exceptionThrown = false;
        try {
            UserInfo userInfo = new UserInfo("foo:");
        } catch (AuthenticationNotFoundException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

}