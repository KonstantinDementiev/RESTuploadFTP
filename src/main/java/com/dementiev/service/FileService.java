package com.dementiev.service;

import java.util.Map;

public interface FileService {

    String getFilesPostRequest();

    boolean loadFileList(Map<String, String> fileList);
}
