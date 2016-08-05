package com.gems.util;

import com.gems.URLList;
import com.gems.URLDepricated;
import com.gems.exception.InvalidInputFileException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by nayana on 8/5/16.
 */
public class InputFileParser {

    public static URLList parse(ArrayList<String> resources) throws InvalidInputFileException
    {
        URLList urlList = new URLList();
        for (String resource : resources) {
            if (InputFileParser.validateResource(resource)) {
                urlList.add(new URLDepricated(resource));
            }
        }

        //if the collection is empty then we have an invalid file
        if (urlList.isEmpty()) {
            throw new InvalidInputFileException();
        }
        //open file read line at a time, send through validateResource
        //on error print invalid line and throw an exception
        return new URLList();
    }

    /**
     *
     * give a filename returns each line in file as an element in an arrayList;
     *
     * @param filename
     * @return ArrayList
     * @throws InvalidInputFileException
     * @throws FileNotFoundException
     */
    public static ArrayList<String> load(String filename) throws InvalidInputFileException, FileNotFoundException
    {
        Scanner inputFileScanner = new Scanner(new File(filename));
        ArrayList<String> resources = new ArrayList<String>();
        while (inputFileScanner.hasNext()){
            resources.add(inputFileScanner.next());
        }
        inputFileScanner.close();

        return resources;
    }

    public static boolean validateResource(String resource)
    {
        return (Pattern.matches("[a-z]+://[a-z0-9A-Z.]+/[a-z0-9A-Z.]+", resource));
    }
}
