package com.wsa.controller;

import com.wsa.model.ResultVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class FileUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResultVO<String> uploadFile(@RequestParam("file") MultipartFile file,
                                       @RequestParam("filename") String filename) {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }

        String cleanFilename = StringUtils.cleanPath(filename) + fileExtension;
        String filePath = Paths.get(uploadDir, cleanFilename).toString();
        File dest = new File(filePath);

        try {
            // 确保上传目录存在
            Files.createDirectories(Paths.get(uploadDir));
            file.transferTo(dest);
            String fileUrl = "/uploads/" + cleanFilename;
            return ResultVO.success(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVO.failure("File upload failed: " + e.getMessage());
        }
    }
}
