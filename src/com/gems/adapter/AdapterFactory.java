package com.gems.adapter;

import com.gems.exception.AdapterNotFoundException;

import java.net.URL;
/**
 * Created by nayana on 8/6/16.
 */
public class AdapterFactory
{
    public static Adapter getAdapter(URL url) throws AdapterNotFoundException
    {
        String protocol = url.getProtocol();
        if (protocol.equals("http") || protocol.equals("ftp")) {
            return new GenericAdapter(url);
        } else if(protocol.equals("sftp")) {
            return new SftpAdapter(url);
        }
        throw new AdapterNotFoundException();
    }
}
