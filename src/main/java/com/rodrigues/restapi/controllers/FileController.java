package com.rodrigues.restapi.controllers;

import com.rodrigues.restapi.data.vo.v1.file.UploadFileResponseVO;
import com.rodrigues.restapi.services.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/api/file/v1")
@Tag(name = "File", description = "Description for File")
public class FileController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private FileStorageService service;


    @PostMapping(value = "/uploadFile")
    @Operation(summary = "Upload a file", description = "Upload a file to the server", tags = {"File"})
    public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file) {
        logger.info("storing file to disk...");

        var filename = service.storeFile(file);

        String downloadURI =
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/api/file/v1/downloadFile/")
                        .path(filename)
                        .toUriString();

        return new UploadFileResponseVO(filename, downloadURI, file.getContentType(), file.getSize());
    }

    @PostMapping(value = "/uploadMultipleFiles")
    @Operation(summary = "Upload a file", description = "Upload a file to the server", tags = {"File"})
    public List<UploadFileResponseVO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        logger.info("storing files to disk...");


        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/downloadFile/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request) {
        logger.info("Reading a file on disk...");

        Resource resource = service.loadFileAsResource(filename);
        String contentType = "";

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            logger.info("Could not determine file type!");
        }

        if(contentType.isEmpty()) contentType = "application/octet-stream";

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\""
                )
                .body(resource);
    }
}
