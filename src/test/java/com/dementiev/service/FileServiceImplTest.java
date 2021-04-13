package com.dementiev.service;

import com.dementiev.files.FileFinder;
import com.dementiev.files.FileReader;
import com.dementiev.ftp.FtpLoader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.testng.Assert.*;

public class FileServiceImplTest {

    private FileServiceImpl fileService;

    @BeforeMethod
    public void setUp() {
        FileFinder fileFinder = new FileFinder();
        FileReader fileReader = new FileReader();
        FtpLoader ftpLoader = new FtpLoader();
        fileService = new FileServiceImpl(fileFinder, fileReader, ftpLoader);
    }

    @Test
    public void getFilesPostRequest_returns_not_null() {
        assertNotNull(fileService.getFilesPostRequest());
    }

    @Test
    public void when_input_file_list_is_incorrect_then_return_false() {
        assertFalse(fileService.loadFileList(null));
        assertFalse(fileService.loadFileList(new HashMap<>()));
        assertFalse(fileService.loadFileList(new HashMap<>() {{
            put("1", "someFile");
        }}));
        assertFalse(fileService.loadFileList(new HashMap<>() {{
            put("1", null);
        }}));
    }

    @Test
    public void when_input_file_list_is_correct_then_return_true() {
        assertTrue(fileService.loadFileList(new HashMap<>() {{
            put(null, "plexon_test_csv_1.csv");
        }}));
        assertTrue(fileService.loadFileList(new HashMap<>() {{
            put("1", "plexon_test_csv_1.csv");
        }}));
    }

}