package com.learn.basic.Learn.Basic.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageService {
    // Service is called by controller
    // Can Call Repository from service
    public String storeFile(MultipartFile file);
    public Stream<Path>  loadAll(); // load all file inside a folder
    public byte[] readFileContent ( String fileName);  // return byte array so that can show it in browser
    public void deleteAllFiles();

}
