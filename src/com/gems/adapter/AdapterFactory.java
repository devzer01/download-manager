package com.gems.adapter;

/**
 * Created by nayan on 8/6/16.
 */
public class AdapterFactory
{
    public static Adapter getAdapter(String protocol)
    {
        if (protocol.equals("http")) {
            return new HttpAdapter();
        }
        return null;
    }
}
