package com.gems.protocol.sftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.net.URLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;


import com.gems.util.UserInfo;
import com.jcraft.jsch.*;
import org.apache.log4j.Logger;
/**
 * Created by nayan on 8/5/16.
 */
public class SftpURLConnection extends URLConnection {

    protected Session session;

    protected Channel channel;

    protected ChannelSftp channelSftp;

    protected JSch jSch;

    protected long size;

    protected SftpURLConnection(URL url)
    {
        super(url);
    }

    protected Logger log = Logger.getLogger(SftpURLConnection.class.getName());


    @Override
    public void connect() throws IOException
    {
        URL url = this.getURL();

        UserInfo userInfo = new UserInfo(url.getUserInfo());
        jSch = new JSch();

        int port = url.getPort();
        if (port == -1) port = url.getDefaultPort();

        try {
            session = jSch.getSession(userInfo.getUsername(), url.getHost(), port);
            session.setPassword(userInfo.getPassword());

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            log.debug("ssh channel open");
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            log.debug("sftp channel open");

        } catch (JSchException e) {
            log.debug("exception in connecting to server, check username password");
            throw new IOException();
        }
    }

    public void disconnect()
    {
        log.debug("disconnecting sftp and ssh channels");
        channelSftp.disconnect();
        session.disconnect();
    }

    public long getContentLengthLong()
    {
        return size;
    }

    public InputStream getInputStream() throws IOException
    {
        try {
            if (channelSftp == null) {
                connect();
            }

            String hostPath = this.getHostPath(url.getFile());
            String remoteFileName = Paths.get(url.getFile()).getFileName().toString();
            channelSftp.cd(hostPath);
            SftpATTRS sftpATTRS = channelSftp.lstat(remoteFileName);
            size = sftpATTRS.getSize();
            return new BufferedInputStream(channelSftp.get(remoteFileName));
        } catch (SftpException e) {
            log.debug("file not found or other stream error");
            throw new IOException();
        }
    }

    protected String getHostPath(String file)
    {
        Path path = Paths.get(file);
        ArrayList<String> pathParts = new ArrayList<String>();
        for (int i=0; i < path.getNameCount() - 1; i++) {
            pathParts.add(path.getName(i).toString());
        }
        String hostPath = String.join("/", pathParts);

        return "/" + hostPath;
    }
}
