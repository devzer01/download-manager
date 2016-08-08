package com.gems.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by nayana on 8/8/16.
 */
public class UserInfo
{
    protected String userPassSplitChar = ":";

    private String username;

    private String password;

    public UserInfo(String userInfo) throws UnsupportedEncodingException, ArrayIndexOutOfBoundsException
    {
        if(userInfo!=null && userInfo.length()>0) {
            String[] userPassPair = userInfo.split(userPassSplitChar);
            username = userPassPair[0];
            password = java.net.URLDecoder.decode(userPassPair[1], "UTF-8");
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
