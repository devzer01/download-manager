package com.gems;

import com.gems.exception.InvalidInputFileException;
import com.gems.protocol.StreamHandlerFactory;
import com.gems.ui.ProgressIndicator;
import com.gems.util.ConfigFile;
import com.gems.model.DownloadList;
import com.gems.util.InputFile;
import com.gems.ui.formatter.Formatter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.lang.reflect.Constructor;


public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("please specify a input file that contains sources to startDownload");
            System.exit(1); //return with status 1 to indicate error
        }

        ConfigFile configFile = null;
        try {
            configFile = new ConfigFile();
        } catch (IOException e) {
            System.out.println("default config file not found");
            System.exit(1);
        }

        DownloadList downloadList = null;

        try {
            downloadList = InputFile.getURLList(args[0]);
        } catch (InvalidInputFileException e) {
            System.out.println("input file format not recognized, or file not found");
            System.exit(1);
        }

        Formatter formatter = getFormatter(configFile);
        ProgressIndicator progressIndicator = new ProgressIndicator(downloadList, formatter);

        //rather than reinventing the wheel
        // i decided to use URLStreamHandlerFactory after reading the documentation here
        //https://docs.oracle.com/javase/7/docs/api/java/net/URL.html
        URL.setURLStreamHandlerFactory(new StreamHandlerFactory());

        DownloadManager downloadManager = new DownloadManager(downloadList, configFile, progressIndicator);
        downloadManager.startDownload();
    }

    /**
     * //NOTE: i was getting an compiler error and had to drop my compiler
     * //version to 1.5 so i had to split the catch statements
     * @param configFile
     * @return
     */
    public static Formatter getFormatter(ConfigFile configFile)
    {
        try {
            Class<?> formatterClass = Class.forName(configFile.getProgressFormatter());
            Constructor<?> constructor = formatterClass.getConstructor();
            Formatter formatter = (Formatter) constructor.newInstance();
            return formatter;
        } catch (ClassNotFoundException e) {
            System.out.println("incorrect formatter in config");
            System.exit(1);
        } catch (NoSuchMethodException e) {
            System.out.println("incorrect formatter in config");
            System.exit(1);
        } catch (IllegalAccessException e) {
            System.out.println("incorrect formatter in config");
            System.exit(1);
        } catch (InstantiationException e) {
            System.out.println("incorrect formatter in config");
            System.exit(1);
        } catch (InvocationTargetException e) {
            System.out.println("incorrect formatter in config");
            System.exit(1);
        }

        return null;
    }
}
