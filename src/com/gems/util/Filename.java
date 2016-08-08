package com.gems.util;

import java.net.URL;
import java.nio.file.Paths;

/**
 * Created by nayan on 8/8/16.
 */
public class Filename
{
    public static String UrlToFilename(URL url)
    {
        return Paths.get(url.getFile()).getFileName().toString();
    }
}
