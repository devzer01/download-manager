package com.gems.util;

import com.gems.exception.AuthenticationNotFoundException;

import java.io.UnsupportedEncodingException;

/**
 * Created by nayana on 8/8/16.
 */
public class UserInfo
{
    protected String userPassSplitChar = ":";

    private String username;

    private String password;

    public UserInfo(String userInfo) throws UnsupportedEncodingException, ArrayIndexOutOfBoundsException, AuthenticationNotFoundException
    {
        if(userInfo!=null && userInfo.length()>0) {
            String[] userPassPair = userInfo.split(userPassSplitChar);
            if (userPassPair.length != 2) {
                throw new AuthenticationNotFoundException();
            }
            username = userPassPair[0];
            password = java.net.URLDecoder.decode(userPassPair[1], "UTF-8");
        } else {
            throw new AuthenticationNotFoundException();
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
