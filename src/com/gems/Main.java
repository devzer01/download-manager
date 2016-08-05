package com.gems;

import com.gems.exception.AdapterNotFoundException;
import com.gems.exception.InvalidInputFileException;
import com.gems.exception.InvalidUrlException;
import com.gems.protocol.StreamHandlerFactory;
import com.gems.util.InputFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.net.URL;


public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("please specify a input file that contains sources to download");
            System.exit(1); //return with status 1 to indicate error
        }

        System.out.println(args[0]);


        URLList urlList = null;
        try {
            urlList = InputFile.getURLList("resources/" + args[0]);
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
        URL.setURLStreamHandlerFactory(new StreamHandlerFactory());

        //iterate through collection (single thread mode first)
        for (URL url : urlList) {
            InputStream inputStream = null;
            FileOutputStream fileOutputStream = null;
            try {
                URLConnection con = url.openConnection();
                inputStream = con.getInputStream();

                //TODO: write to tempfile and move when done
                fileOutputStream = new FileOutputStream("resources/" + url.getFile());

                int bytesRead = -1;
                byte[] buffer = new byte[4096];

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }

            } catch (IOException e) {

            } finally {
                try {
                    inputStream.close();
                    fileOutputStream.close();
                } catch (IOException e) {

                }
            }
        }


        //multi thread mode

    }
}
