package com.gems.protocol.sftp;

import java.io.IOException;
import java.net.URLConnection;
import java.net.URL;

import com.jcraft.jsch.*;

/**
 * Created by nayan on 8/5/16.
 */
public class SftpURLConnection extends URLConnection {

    protected SftpURLConnection(URL url)
    {
        super(url);
    }

    @Override
    public void connect() throws IOException
    {
        URL url = this.getURL();
        String username = "";
        String password = "";

        String userInfo = url.getUserInfo();

        if(userInfo!=null && userInfo.length()>0) {
            String[] userPassPair = userInfo.split(":");
            username = userPassPair[0];
            password = userPassPair[1]; //arrayOutofBoundException???
        }
        JSch jsch=new JSch();
        try {
            Session session = jsch.getSession(username, url.getHost(), url.getDefaultPort());
            session.setPassword(password);

            Channel     channel     = null;
            ChannelSftp channelSftp = null;

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp)channel;
            channelSftp.cd(url.getPath());
            byte[] buffer = new byte[1024];
            /*BufferedInputStream bis = new BufferedInputStream(channelSftp.get(url.getFile()));
            File newFile = new File("C:/Test.java");
            OutputStream os = new FileOutputStream(newFile);
            BufferedOutputStream bos = new BufferedOutputStream(os);*/
            int readCount;
//System.out.println("Getting: " + theLine);
            //while( (readCount = bis.read(buffer)) > 0) {
                System.out.println("Writing: " );
                //bos.write(buffer, 0, readCount);
            //}
            //bis.close();
            //bos.close();

        } catch (JSchException e) {
            System.out.println(e.getMessage());
        } catch (SftpException e) {
            System.out.println(e.getMessage());
        }
    }
}
