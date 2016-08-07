package com.gems;

import com.gems.exception.InvalidInputFileException;
import com.gems.protocol.StreamHandlerFactory;
import com.gems.util.ConfigFile;
import com.gems.util.DownloadList;
import com.gems.util.InputFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;


public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("please specify a input file that contains sources to download");
            System.exit(1); //return with status 1 to indicate error
        }

        System.out.println(args[0]);


        DownloadList downloadList = null;
        ConfigFile configFile = null;
        try {
            downloadList = InputFile.getURLList("resources/" + args[0]);
            configFile = ConfigFile.parse(null);
        } catch (InvalidInputFileException e) {
            //throw error if input file not valid
            System.out.println("input file format not recognized");
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.out.println("input file format not recognized");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("default config file not found");
            System.exit(1);
        }

        //rather than reinventing the wheel
        // i decided to use URLStreamHandlerFactory after reading the documentation here
        //https://docs.oracle.com/javase/7/docs/api/java/net/URL.html
        URL.setURLStreamHandlerFactory(new StreamHandlerFactory());

        DownloadManager downloadManager = new DownloadManager(downloadList, configFile.getDownloadFolder());
        downloadManager.download();

        //multi thread mode

    }
}
