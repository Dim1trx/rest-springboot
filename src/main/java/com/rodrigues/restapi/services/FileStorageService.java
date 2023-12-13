package com.rodrigues.restapi.services;

import com.rodrigues.restapi.config.FileStorageConfig;
import com.rodrigues.restapi.exceptions.FileStorageException;
import com.rodrigues.restapi.exceptions.MyFileNotFoundExceptioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig config) {
        Path path = Paths.get(config.getUploadDir()).toAbsolutePath().normalize();

        this.fileStorageLocation = path;

        try {
            Files.createDirectories(this.fileStorageLocation);

        } catch (Exception ex) {
            throw new FileStorageException(
                    "Couldn`t create the directory where the uploaded files will be stored!",
                    ex
            );
        }
    }

    public String storeFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());


        try {
            if(filename.contains(".."))
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + filename);


            Path targetLocation = this.fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        }
        catch (Exception ex) {
            throw new FileStorageException
                    ("Could not store file " + filename + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String filename) {
        try{
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) return resource;
            else throw new MyFileNotFoundExceptioException("File not found " + filename);


        }
        catch (Exception ex) {
            throw new MyFileNotFoundExceptioException("File not found " + filename, ex);
        }
    }
}
