package com.gems.protocol.sftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.net.URLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;


import com.gems.util.Filename;
import com.gems.util.UserInfo;
import com.jcraft.jsch.*;
import org.apache.log4j.Logger;

/**
 * Created by nayana on 8/5/16.
 *
 * implementation of URLConnection using Jsch for sftp
 */
public class SftpURLConnection extends URLConnection implements DisconnectStream {

    protected Session session;

    protected ChannelSftp channelSftp;

    protected JSch jSch;

    protected long size;

    /**
     * log4j logger
     */
    protected Logger log = Logger.getLogger(SftpURLConnection.class.getName());

    protected SftpURLConnection(URL url)
    {
        super(url);
    }

    /**
     * helper function to return the port
     * @return
     */
    protected int getSftpPort()
    {
        int port = url.getPort();
        if (port == -1) port = url.getDefaultPort();
        return port;
    }

    /**
     *
     * establish ssh connection and then open sftp stream on-top of it
     *
     * @throws IOException
     */
    @Override
    public void connect() throws IOException
    {
        UserInfo userInfo = new UserInfo(url.getUserInfo());
        jSch = new JSch();

        try {
            //start ssh connection
            session = jSch.getSession(userInfo.getUsername(), url.getHost(), getSftpPort());
            session.setPassword(userInfo.getPassword());

            //prevent finger-print check so we can connect to an
            //unknown host
            java.util.Properties sshConfig = new java.util.Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);
            session.setTimeout(getConnectTimeout()); //sets the timeout obtain from urlConnection

            session.connect();
            log.debug(url.getHost() + " ssh channel open");

            //This channel can also be opened during getStream()
            //but since we going to handle JSchexception here keeping it here
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            log.debug(url.getHost() + " sftp channel open");

        } catch (JSchException e) {
            log.debug(url.getHost() + " exception in connecting to server, check username password");
            throw new IOException();
        }
    }

    /**
     * for sftp we need to explicitly disconnect ssh and sftp
     */
    public void disconnect()
    {
        log.debug("disconnecting sftp and ssh channels");
        channelSftp.disconnect();
        session.disconnect();
    }

    /**
     *
     * @return long
     */
    public long getContentLengthLong()
    {
        return size;
    }

    /**
     * change working directory to remote path
     * get the size of the file using attributes
     * and open a stream to download the file
     *
     * @return InputStream
     * @throws IOException
     */
    public InputStream getInputStream() throws IOException
    {
        try {
            if (channelSftp == null) connect();

            channelSftp.cd(getHostPath(url.getFile()));
            size  = channelSftp.lstat(Filename.UrlToFilename(url)).getSize();
            return new BufferedInputStream(channelSftp.get(Filename.UrlToFilename(url)));

        } catch (SftpException e) {
            log.debug("file not found or other stream error");
            throw new IOException();
        }
    }

    /**
     * need to parse the getPath
     *
     * @param file
     * @return
     */
    protected String getHostPath(String file)
    {
        Path path = Paths.get(file);
        ArrayList<String> pathParts = new ArrayList<>();
        for (int i=0; i < path.getNameCount() - 1; i++) {
            pathParts.add(path.getName(i).toString());
        }
        //since all remote servers use unix-style path seperator
        //this doesn't become platform dependent
        String hostPath = String.join("/", pathParts);

        return "/" + hostPath;
    }
}
