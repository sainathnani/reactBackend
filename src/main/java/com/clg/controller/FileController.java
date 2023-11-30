package com.clg.controller;


import com.clg.model.Files;
import com.clg.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/add")
    public String addResume(@RequestParam("userName") String userName,
                            @RequestParam("file") MultipartFile file,
                            @RequestParam("fileType") String fileType)
            throws IOException {
        Long id = fileService.addResume(userName, file, fileType);
        return "Success";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> downloadResume(@PathVariable ("fileId") Long fileId)
            throws IOException {
        Files loadFile = fileService.downloadFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(loadFile.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + loadFile.getOriginalFileName() + "\"")
                .body(new ByteArrayResource(loadFile.getFile().getData()));
    }
}
