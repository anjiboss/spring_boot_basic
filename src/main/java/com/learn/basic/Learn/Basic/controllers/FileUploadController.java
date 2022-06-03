package com.learn.basic.Learn.Basic.controllers;

import com.learn.basic.Learn.Basic.models.ResponseObject;
import com.learn.basic.Learn.Basic.services.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "/api/v1/FileUpload")
public class FileUploadController {
    // this controller receives file/images from client

    // Inject Storage Service here
    @Autowired
    private IStorageService storageService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile (@RequestParam("file")MultipartFile file){
        try {
            // Save file to server
            String generatedFilename = storageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Upload Complete", generatedFilename)
            );
        }catch ( Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", exception.getMessage(), "")
            );
        }
    }
}
