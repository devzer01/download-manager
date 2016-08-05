package com.gems.adapter;

import java.net.URL;
/**
 * Created by nayana on 8/5/16.
 */
public class HTTPAdapter extends Adapter {

    public HTTPAdapter(URL url)
    {
        super(url);
    }

    public void download() {

    }

    public boolean validate()
    {

        return false;
    }
}
