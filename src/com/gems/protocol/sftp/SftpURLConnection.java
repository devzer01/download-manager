package com.gems.protocol.sftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.net.URLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;

import com.jcraft.jsch.*;

/**
 * Created by nayan on 8/5/16.
 */
public class SftpURLConnection extends URLConnection {

    protected Session session;

    protected Channel channel;

    protected ChannelSftp channelSftp;

    protected JSch jSch;

    protected SftpURLConnection(URL url)
    {
        super(url);
    }

    @Override
    public void connect() throws IOException
    {
        URL url = this.getURL();

        /*** refactor
         *
         */
        String username = "";
        String password = "";

        String userInfo = url.getUserInfo();

        if(userInfo!=null && userInfo.length()>0) {
            String[] userPassPair = userInfo.split(":");
            username = userPassPair[0];
            password = userPassPair[1]; //arrayOutofBoundException???
        }


        jSch = new JSch();

        try {
            session = jSch.getSession("nayana", url.getHost(), url.getDefaultPort());
            session.setPassword("ZaQ1#wSx");

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            System.out.print("connected sftp");

        } catch (JSchException e) {
            System.out.println("Exception in connect() handler inputstream");
            throw new IOException();
        }
    }

    public void disconnect()
    {
        System.out.println("call disconnect ---");
        channel.disconnect();
        channelSftp.disconnect();
        session.disconnect();
    }

    public InputStream getInputStream() throws IOException
    {
        try {
            if (channelSftp == null) {
                connect();
            }

            Path path = Paths.get(url.getFile());
            ArrayList<String> pathParts = new ArrayList<String>();
            //we need to build base path
            for (int i=0; i < path.getNameCount() - 1; i++) {
                pathParts.add(path.getName(i).toString());
            }
            String hostPath = String.join("/", pathParts);

            channelSftp.cd("/" + hostPath);
            return new BufferedInputStream(channelSftp.get(path.getFileName().toString()));
        } catch (SftpException e) {
            System.out.println("Exception in connection handler inputstream");
            throw new IOException();
        }
    }
}
