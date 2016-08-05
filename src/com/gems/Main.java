package com.gems;

import com.gems.exception.InvalidInputFileException;
import com.gems.util.InputFileParser;

public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("please specify a input file that contains sources to download");
            System.exit(1); //return with status 1 to indicate error
        }

        URLList urlList = null;
        try {
            urlList = InputFileParser.parse(args[0]);
        } catch (InvalidInputFileException e) {
            //throw error if input file not valid
            System.out.println("input file format not recognized");
            System.exit(1);
        }

        //iterate through collection (single thread mode first)
        for (URL u : urlList) {

        }


        //multi thread mode

    }
}
