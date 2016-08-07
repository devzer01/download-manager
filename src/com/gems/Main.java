package com.gems;

import com.gems.exception.InvalidInputFileException;
import com.gems.protocol.StreamHandlerFactory;
import com.gems.ui.ProgressIndicator;
import com.gems.util.ConfigFile;
import com.gems.model.DownloadList;
import com.gems.util.InputFile;
import com.gems.ui.formatter.Formatter;
import com.gems.model.Job;

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
        URL.setURLStreamHandlerFactory(new StreamHandlerFactory());

        try {
            downloadList = InputFile.getURLList(args[0]);
        } catch (InvalidInputFileException e) {
            System.out.println("input file format not recognized, or file not found");
            System.exit(1);
        }

        Formatter formatter = getFormatter(configFile);
        ProgressIndicator progressIndicator = new ProgressIndicator(formatter);
        Job job = new Job(downloadList, progressIndicator);

        DownloadManager downloadManager = new DownloadManager(job, configFile);
        downloadManager.startDownload();
    }

    /**
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
        } catch (ClassNotFoundException|NoSuchMethodException|IllegalAccessException|InstantiationException|InvocationTargetException e) {
            System.out.println("incorrect formatter in config");
            System.exit(1);
        }

        return null;
    }
}
