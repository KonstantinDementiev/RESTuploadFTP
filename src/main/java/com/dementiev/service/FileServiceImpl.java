package com.dementiev.service;

import com.dementiev.files.FileFinder;
import com.dementiev.files.FileReader;
import com.dementiev.ftp.FtpLoader;
import com.dementiev.logger.Logger;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    private final static Logger LOG = new Logger(FileServiceImpl.class.getName());
    private final static Path DIRECTORY_PATH = Paths.get("G:\\JavaBuffer");
    private final FileFinder fileFinder;
    private final FileReader fileReader;
    private final FtpLoader ftpLoader;
    private String printedFileName;

    @Autowired
    public FileServiceImpl(
            FileFinder fileFinder,
            FileReader fileReader,
            FtpLoader ftpLoader
    ) {
        this.fileFinder = fileFinder;
        this.fileReader = fileReader;
        this.ftpLoader = ftpLoader;
    }

    @Override
    public String getFilesPostRequest() {
        Map<String, String> fileList = new HashMap<>();
        fileList.put("1", "plexon_test_csv_1.csv");
        fileList.put("2", "plexon_test_csv_2.csv");
        return getPostRequest(fileList);
    }

    private String getPostRequest(Map<String, String> fileList) {
        return "fetch('/files/load', { method: 'POST', " +
                "headers: {'Content-Type': 'application/json'}, " +
                "body: JSON.stringify(" + getJson(fileList) + ")}).then(result => console.log(result))";
    }

    private String getJson(Map<String, String> fileList) {
        Gson gson = new Gson();
        return gson.toJson(fileList);
    }

    @Override
    public boolean loadFileList(Map<String, String> fileList) {
        if (fileList == null || fileList.isEmpty()) {
            LOG.info("Input file list is empty.");
            return false;
        }
        try {
            for (String fileName : fileList.values()) {
                printedFileName = fileName;
                Path file = findFile(fileName);
                printFileToConsole(readFile(file));
                uploadToFTP(file);
            }
        } catch (IOException | IllegalArgumentException e) {
            LOG.error("Can not handle file: '" + printedFileName + "'.", e);
            return false;
        }
        return true;
    }

    private Path findFile(String fileName) {
        Path file = fileFinder.findFile(fileName, DIRECTORY_PATH);
        printedFileName = fileName;
        LOG.info("The file '" + printedFileName + "' has been found.");
        return file;
    }

    private List<String> readFile(Path file) throws IOException {
        List<String> strList = fileReader.readFromFile(file);
        LOG.info("The file '" + printedFileName + "' has been read.");
        return strList;
    }

    private void printFileToConsole(List<String> file) {
        file.forEach(System.out::println);
        LOG.info("The file '" + printedFileName + "' has been printed.");
    }

    private void uploadToFTP(Path filName){
        try {
            ftpLoader.uploadFile(filName, "filName", "url", "login", "password");
        } catch (FileNotFoundException e) {
            LOG.warning("The file '" + printedFileName + "' not found.", e);
        }
    }

}


