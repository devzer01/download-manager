package com.gems;

import com.gems.exception.InvalidInputFileException;
import com.gems.protocol.StreamHandlerFactory;
import com.gems.ui.ConsoleProgressIndicator;
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
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;


public class Main {

    protected static Logger log = Logger.getLogger(Main.class.getName());

    public static void errorExit(String string)
    {
        System.out.println(string);
        System.exit(1);
    }

    public static void main(String[] args) {

        log.info("Starting downloader");

        if (args.length == 0) {
            errorExit("please specify a input file that contains sources to startDownload");
        }


        ConfigFile configFile = null;
        try {
            configFile = new ConfigFile();
        } catch (IOException e) {
            errorExit("default config file not found");
        }

        //validate if download directory exists and can write a file to it
        if (Files.notExists(Paths.get(configFile.getDownloadFolder()))) {
            errorExit("configured download directory doesn't exist");
        }

        if (!Files.isWritable(Paths.get(configFile.getDownloadFolder()))) {
            errorExit("download directory is not writable");
        }

        DownloadList downloadList = null;
        URL.setURLStreamHandlerFactory(new StreamHandlerFactory());

        try {
            downloadList = InputFile.getURLList(args[0]);
        } catch (InvalidInputFileException e) {
            errorExit("input file format not recognized, or file not found");
        }

        Formatter formatter = getFormatter(configFile);
        ProgressIndicator progressIndicator = new ConsoleProgressIndicator(formatter);
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
            errorExit("incorrect formatter in config");
        }

        return null;
    }
}
