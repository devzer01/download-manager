package com.gems;

import com.gems.adapter.Adapter;
import com.gems.adapter.HTTPAdapter;
import com.gems.exception.AdapterNotFoundException;
import com.gems.exception.InvalidUrlException;
import java.net.URL;
import java.net.URLStreamHandler;

/**
 * Created by nayan on 8/5/16.
 */
public class AdapterFactory implements java.net.URLStreamHandlerFactory {

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        return null;
    }
}
