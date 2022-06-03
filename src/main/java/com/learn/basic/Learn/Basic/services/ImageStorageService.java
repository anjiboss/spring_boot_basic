package com.learn.basic.Learn.Basic.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageStorageService  implements  IStorageService{
    private final Path storageFolder = Paths.get("uploads");

    public ImageStorageService () {
        try {
            Files.createDirectories(storageFolder);
        }catch (IOException exception){
            throw  new RuntimeException("Cannot initialize storage", exception);
        }
    }

    private  boolean isImageFile (MultipartFile file){
        // Check if is images using  FileNameUtils
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        assert fileExtension != null; // Assert that fileExtension will never be null
        return Arrays.asList(new String[] {"png", "jpg", "jpeg", "bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public String storeFile(MultipartFile file) {
        try {
            System.out.println("haha?");
            if (file.isEmpty()){
                throw new RuntimeException("Failed to store empty file");
            }
            if (!isImageFile(file)){
                throw new RuntimeException("You can only upload image file");
            }

            // file must be <= 5MB
            float fileSizeInMegaByte = file.getSize() / 1_000_000;
            if (fileSizeInMegaByte > 5.0f){
                throw new RuntimeException("File must be <= 5MB");
            }

            // Change filename
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFilename = UUID.randomUUID().toString().replace("-", "");
            generatedFilename = generatedFilename + "."+ fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(
                    Paths.get(generatedFilename))
                    .normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())){
                throw  new RuntimeException("Cannot store file outside current directory");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generatedFilename;

        }catch (IOException exception){
            throw new RuntimeException("Failed to store file." , exception);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public byte[] readFileContent(String fileName) {
        return new byte[0];
    }

    @Override
    public void deleteAllFiles() {

    }
}
