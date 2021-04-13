package com.dementiev.controllers;

import com.dementiev.service.FileService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
public class FileControllerTest extends AbstractTestNGSpringContextTests {

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private FileController fileController;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @Test
    public void testNewFiles() throws Exception {
        this.mockMvc.perform(get("/files/new"))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoadFileList_success() throws Exception {
        Gson gson = new Gson();
        Map<String, String> fileList = new HashMap<>();
        fileList.put("1", "plexon_test_csv_1.csv");
        String strContent = gson.toJson(fileList);
        this.mockMvc.perform(post("/files/load")
                .content(strContent)
                .contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLoadFileList_error() throws Exception {
        this.mockMvc.perform(post("/files/load")
                .content("something")
                .contentType(contentType))
                .andExpect(status().isBadRequest());
        this.mockMvc.perform(post("/someUlr"))
                .andExpect(status().isNotFound());
    }
}