package com.dementiev.ftp;

import com.dementiev.logger.Logger;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

@Component
public class FtpLoader {

    private final static Logger LOG = new Logger(FtpLoader.class.getName());

    public void uploadFile(Path filePathOnPC,
                           String filePathOnFTP,
                           String url,
                           String login,
                           String password) throws FileNotFoundException {

        FTPClient ftp = new FTPClient();
        FileInputStream fis = new FileInputStream(String.valueOf(filePathOnPC));

        try {
            ftp.connect(url);
            ftp.enterLocalPassiveMode();
            ftp.login(login, password);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                LOG.info("Connection error.");
            } else {
                ftp.storeFile(filePathOnFTP, fis);
                ftp.logout();
            }
            ftp.disconnect();
            LOG.info("The file '" + filePathOnPC.getFileName() + "' was uploaded on FTP successfully.");
        } catch (IOException e) {
            LOG.error("Unable to upload file to FTP", e);
        }
    }

}


