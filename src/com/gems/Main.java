package com.gems;

import com.gems.exception.AdapterNotFoundException;
import com.gems.exception.InvalidInputFileException;
import com.gems.exception.InvalidUrlException;
import com.gems.util.InputFileParser;
import com.gems.adapter.Adapter;

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.net.URL;


public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("please specify a input file that contains sources to download");
            System.exit(1); //return with status 1 to indicate error
        }


        URLList urlList = null;
        try {
            ArrayList<String> resources = InputFileParser.load(args[0]);
            urlList = InputFileParser.parse(resources);

        } catch (InvalidInputFileException e) {
            //throw error if input file not valid
            System.out.println("input file format not recognized");
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.out.println("input file format not recognized");
            System.exit(1);
        }

        //rather than reinventing the wheel
        // i decided to use URLStreamHandlerFactory after reading the documentation here
        //https://docs.oracle.com/javase/7/docs/api/java/net/URL.html
        URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory());

        //iterate through collection (single thread mode first)
        for (URL u : urlList) {
            Adapter adapter = null;
            try {
                adapter = AdapterFactory.getAdapter(u);
                adapter.validate();
            } catch (InvalidUrlException|AdapterNotFoundException e) {
                System.out.println("input file format not recognized");
            }

            adapter.download();

        }


        //multi thread mode

    }
}
