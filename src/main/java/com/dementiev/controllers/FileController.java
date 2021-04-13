package com.dementiev.controllers;

import com.dementiev.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/new")
    public String newFiles() {
        return fileService.getFilesPostRequest();
    }

    @PostMapping("/load")
    public ResponseEntity<?> loadFileList(@RequestBody Map<String, String> fileList) {
        boolean success = fileService.loadFileList(fileList);
        return getResponse(success);
    }

    private ResponseEntity<?> getResponse(boolean success) {
        return success
                ? new ResponseEntity<>("All files have been loaded successfully!", HttpStatus.OK)
                : new ResponseEntity<>("Unfortunately, server can not load file(s).", HttpStatus.BAD_REQUEST);
    }


}
