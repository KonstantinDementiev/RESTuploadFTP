package com.dementiev.files;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileReaderTest {
    private static final Path DIRECTORY_PATH = Paths.get("G:\\JavaBuffer");
    private FileReader fileReader;
    private Path readingFile;
    private Path tempFile;

    @BeforeMethod
    public void setUp() throws IOException {
        readingFile = Paths.get("G:\\JavaBuffer\\plexon_test_csv_1.csv");
        tempFile = DIRECTORY_PATH.resolve("testFile.tmp");
        Files.deleteIfExists(tempFile);
        Files.createFile(tempFile);
        fileReader = new FileReader();
    }

    @Test
    public void when_reading_from_null_return_empty_list() throws IOException {
        List<String> result = fileReader.readFromFile(null);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void when_reading_from_empty_file_return_empty_list() throws IOException {
        List<String> result = fileReader.readFromFile(tempFile);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void reading_from_file_with_correct_path_is_successful() throws IOException {
        List<String> result = fileReader.readFromFile(readingFile);
        Assert.assertFalse(result.isEmpty());
    }

    @AfterMethod
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }
}