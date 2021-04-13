package com.dementiev.files;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.testng.Assert.*;

public class FileFinderTest {
    private static final String DIRECTORY_PATH = "G:\\JavaBuffer";
    private static final String FILE_NAME = "plexon_test_csv_1.csv";
    private FileFinder fileFinder;

    @BeforeMethod
    public void setUp() {
        fileFinder = new FileFinder();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_file_name_is_null_throw_exception() {
        fileFinder.findFile(null, Paths.get(DIRECTORY_PATH));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_file_name_is_empty_throw_exception() {
        fileFinder.findFile("", Paths.get(DIRECTORY_PATH));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_directory_path_is_null_throw_exception() {
        fileFinder.findFile(FILE_NAME, null);
    }

    @Test
    public void when_path_is_correct_find_existing_files_successfully() {
        fileFinder.findFile(FILE_NAME, Paths.get(DIRECTORY_PATH));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_directory_path_is_incorrect_throw_exception() {
        fileFinder.findFile(FILE_NAME, Paths.get("K:\\Users\\"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_file_name_is_incorrect_throw_exception() {
        fileFinder.findFile("file.txt", Paths.get(DIRECTORY_PATH));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void when_find_more_then_one_file_throw_exception() throws IOException {
        Path testDirectory = Paths.get(DIRECTORY_PATH).resolve("testFolder");
        Files.createDirectory(testDirectory);
        Files.createFile(testDirectory.resolve(FILE_NAME));
        fileFinder.findFile(FILE_NAME, Paths.get(DIRECTORY_PATH));
        Files.deleteIfExists(testDirectory.resolve(FILE_NAME));
        Files.deleteIfExists(testDirectory);
    }

    @AfterMethod
    public void tearDown() throws IOException {
        Path testDirectory = Paths.get(DIRECTORY_PATH).resolve("testFolder");
        Files.deleteIfExists(testDirectory.resolve(FILE_NAME));
        Files.deleteIfExists(testDirectory);
    }
}