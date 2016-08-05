package com.gems.util;

import com.gems.URLList;
import com.gems.exception.InvalidInputFileException;
/**
 * Created by nayana on 8/5/16.
 */
public class InputFileParser {

    public static URLList parse(String filename) throws InvalidInputFileException
    {
        //open file read line at a time, send through validateResource
        //on error print invalid line and throw an exception
        return new URLList();
    }

    private static boolean validateResource(String resource)
    {
        return false;
    }
}
