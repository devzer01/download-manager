package com.gems.util;

import com.gems.DownloadList;
import com.gems.DownloadableFile;
import com.gems.Progress;
import com.gems.exception.InvalidInputFileException;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by nayana on 8/5/16.
 */
public class InputFile
{
    public static DownloadList getURLList(String filename) throws InvalidInputFileException, FileNotFoundException
    {
        return InputFile.parse(InputFile.load(filename));
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
    private static ArrayList<String> load(String filename) throws InvalidInputFileException, FileNotFoundException
    {
        Scanner inputFileScanner = new Scanner(new File(filename));
        ArrayList<String> resources = new ArrayList<String>();
        while (inputFileScanner.hasNext()){
            resources.add(inputFileScanner.next());
        }
        inputFileScanner.close();

        return resources;
    }

    /**
     *
     * @param resources
     * @return
     * @throws InvalidInputFileException
     */
    private static DownloadList parse(ArrayList<String> resources) throws InvalidInputFileException
    {
        DownloadList downloadList = new DownloadList();
        for (String resource : resources) {
            try {
                downloadList.add(new DownloadableFile(new URL(resource), new Progress("initialized")));
            } catch (MalformedURLException e) {
                System.out.println("invalid url " + resource);
            }
        }

        //if the collection is empty then we have an invalid file
        if (downloadList.isEmpty()) {
            throw new InvalidInputFileException();
        }
        //open file read line at a time, send through validateResource
        //on error print invalid line and throw an exception
        return downloadList;
    }


}
