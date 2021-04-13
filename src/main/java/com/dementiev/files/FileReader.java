package com.dementiev.files;

import com.dementiev.logger.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class FileReader {

    private final static Logger LOG = new Logger(FileReader.class.getName());

    public List<String> readFromFile(Path inputFile) throws IOException {
        List<String> result = new ArrayList<>();
        if (isFileValid(inputFile)) {
            try (BufferedReader reader = Files.newBufferedReader(inputFile, UTF_8)) {
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    result.add(currentLine);
                }
            } catch (IOException e) {
                LOG.error("Can not read file: " + inputFile, e);
                throw new IOException("Can not read file: " + inputFile, e);
            }
        }
        return result;
    }

    private boolean isFileValid(Path file) {
        if (file == null) {
            LOG.info("File path is null.");
            return false;
        }
        if (!Files.exists(file)) {
            LOG.info("File '" + file.getFileName() + "' does not exist.");
            return false;
        }
        if (!Files.isReadable(file)) {
            LOG.info("File '" + file.getFileName() + "' is not readable.");
            return false;
        }
        return true;
    }

}
