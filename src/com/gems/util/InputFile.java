package com.gems.util;

import com.gems.exception.InvalidInputFileException;
import com.gems.model.DownloadList;
import com.gems.model.Task;
import com.gems.model.Progress;

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
    /**
     *
     * @param filename
     * @return
     * @throws InvalidInputFileException
     */
    public static DownloadList getURLList(String filename) throws InvalidInputFileException
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
     */
    private static ArrayList<String> load(String filename) throws InvalidInputFileException
    {
        try {
            Scanner inputFileScanner = new Scanner(new File(filename));
            ArrayList<String> resources = new ArrayList<String>();
            while (inputFileScanner.hasNext()) {
                resources.add(inputFileScanner.next());
            }
            inputFileScanner.close();
            return resources;
        } catch (FileNotFoundException e) {
            throw new InvalidInputFileException();
        }
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
                downloadList.put(resource, new Task(new URL(resource), new Progress("initialized")));
            } catch (MalformedURLException e) {
                System.out.println("invalid url " + resource);
            }
        }

        //if the collection is empty then we have an invalid file
        if (downloadList.isEmpty()) {
            throw new InvalidInputFileException();
        }

        return downloadList;
    }


}
