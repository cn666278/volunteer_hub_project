package com.wsa.controller;

import com.wsa.model.*;
import com.wsa.service.FileService;
import com.wsa.service.UserService;
import com.wsa.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileUploadController {

    @Autowired
    private FileService fileService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private UserService userService;

    @PostMapping("/upload")
    public ResultVO<String> uploadFile(@RequestParam("file") MultipartFile file,
                                       @RequestParam("filename") String filename) {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }
        String cleanFilename = StringUtils.cleanPath(filename) + fileExtension;
        try {
            FileModel fileModel = new FileModel();
            fileModel.setFilename(cleanFilename);
            fileModel.setContentType(file.getContentType());
            fileModel.setData(file.getBytes());
            fileService.saveFile(fileModel);
            return ResultVO.success("File uploaded successfully with ID: " + fileModel.getId());
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVO.failure("File upload failed");
        }
    }

    @PostMapping("/uploadForVolunteer")
    public ResultVO<String> uploadFileForVolunteer(@RequestParam("file") MultipartFile file,
                                       @RequestParam("filename") String filename,
                                                   @RequestParam("volunteerId") Long volunteerId) {
        Volunteer volunteer = volunteerService.getVolunteerByUserId(volunteerId);

        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }
        String cleanFilename = StringUtils.cleanPath(filename) + fileExtension;
        try {
            FileModel fileModel = new FileModel();
            fileModel.setFilename(cleanFilename);
            fileModel.setContentType(file.getContentType());
            fileModel.setData(file.getBytes());
            fileService.saveFile(fileModel);
            volunteerService.addCredential(volunteer.getId(),filename,"/files/"+fileModel.getId());
            return ResultVO.success("File uploaded successfully with ID: " + fileModel.getId());
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVO.failure("File upload failed");
        }
    }

    @PostMapping("/uploadAvatorForVolunteer")
    public ResultVO<String> uploadAvatorForVolunteer(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("filename") String filename,
                                                   @RequestParam("volunteerId") Long volunteerId) {


        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }
        String cleanFilename = StringUtils.cleanPath(filename) + fileExtension;
        try {
            FileModel fileModel = new FileModel();
            fileModel.setFilename(cleanFilename);
            fileModel.setContentType(file.getContentType());
            fileModel.setData(file.getBytes());
            fileService.saveFile(fileModel);
            userService.updateUserAvator(volunteerId,"/files/"+fileModel.getId());
            return ResultVO.success("File uploaded successfully with ID: " + fileModel.getId());
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVO.failure("File upload failed");
        }
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<String> getFile(@PathVariable Long id) {
        FileModel fileModel = fileService.getFileById(id);
        if (fileModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        String base64Data = Base64Utils.encodeToString(fileModel.getData());

        return new ResponseEntity<>(base64Data, headers, HttpStatus.OK);
    }
}
