package com.dementiev.files;

import com.dementiev.logger.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FileFinder {

    private final static Logger LOG = new Logger(FileFinder.class.getName());

    public Path findFile(String fileName, Path directory) {
        String validFileName = getValidFileName(fileName);
        Path validDirectoryPath = getValidDirectoryPath(directory);
        try {
            Collection<Path> allPaths = getPathsByFileName(validFileName, validDirectoryPath);
            return getOnlyOnePath(validFileName, validDirectoryPath, allPaths);
        } catch (IOException e) {
            LOG.warning("Can not find directory: " + directory, e);
            throw new IllegalArgumentException("Can not find directory: " + directory, e);
        }
    }

    private String getValidFileName(String fileName) {
        Optional<String> optFileName = Optional.ofNullable(fileName);
        return optFileName.orElseThrow(() -> new IllegalArgumentException("File name is empty"));
    }

    private Path getValidDirectoryPath(Path directory) {
        Optional<Path> optPath = Optional.ofNullable(directory);
        return optPath.orElseThrow(() -> new IllegalArgumentException("Directory path is empty"));
    }

    private Collection<Path> getPathsByFileName(String fileName, Path directory) throws IOException {
        try (Stream<Path> files = Files.walk(directory)) {
            return files
                    .filter(f -> f.getFileName().toString().equals(fileName))
                    .collect(Collectors.toList());
        }
    }

    private Path getOnlyOnePath(String fileName, Path directory, Collection<Path> allPaths) {
        if (allPaths.size() > 1) {
            LOG.info("There are more than 1 file were found:");
            allPaths.forEach(item -> LOG.info("-> " + item.normalize().toString()));
            LOG.info("Please, specify folder name for file needed");
        } else if (allPaths.isEmpty()) {
            LOG.info("Can not find file '" + fileName + "' in the directory: " + directory);
        } else {
            return allPaths.iterator().next();
        }
        throw new IllegalArgumentException();
    }

}
