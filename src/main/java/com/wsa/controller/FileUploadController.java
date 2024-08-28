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
    private FileService fileService; // Service for handling file operations

    @Autowired
    private VolunteerService volunteerService; // Service for managing volunteer-related operations

    @Autowired
    private UserService userService; // Service for managing user-related operations

    /**
     * Endpoint to upload a generic file.
     * @param file The file to be uploaded.
     * @param filename The name to save the file as.
     * @return ResultVO containing success or failure message.
     */
    @PostMapping("/upload")
    public ResultVO<String> uploadFile(@RequestParam("file") MultipartFile file,
                                       @RequestParam("filename") String filename) {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }
        String cleanFilename = StringUtils.cleanPath(filename) + fileExtension; // Sanitize filename to prevent directory traversal attacks
        try {
            FileModel fileModel = new FileModel();
            fileModel.setFilename(cleanFilename);
            fileModel.setContentType(file.getContentType());
            fileModel.setData(file.getBytes()); // Convert file to byte array
            fileService.saveFile(fileModel); // Save file to database
            return ResultVO.success("File uploaded successfully with ID: " + fileModel.getId());
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVO.failure("File upload failed");
        }
    }

    /**
     * Endpoint to upload a file specific to a volunteer.
     * @param file The file to be uploaded.
     * @param filename The name to save the file as.
     * @param volunteerId The ID of the volunteer to associate the file with.
     * @return ResultVO containing success or failure message.
     */
    @PostMapping("/uploadForVolunteer")
    public ResultVO<String> uploadFileForVolunteer(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("filename") String filename,
                                                   @RequestParam("volunteerId") Long volunteerId) {
        Volunteer volunteer = volunteerService.getVolunteerByUserId(volunteerId); // Get volunteer by ID

        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }
        String cleanFilename = StringUtils.cleanPath(filename) + fileExtension; // Sanitize filename
        try {
            FileModel fileModel = new FileModel();
            fileModel.setFilename(cleanFilename);
            fileModel.setContentType(file.getContentType());
            fileModel.setData(file.getBytes()); // Convert file to byte array
            fileService.saveFile(fileModel); // Save file to database
            volunteerService.addCredential(volunteer.getId(), filename, "/files/" + fileModel.getId()); // Associate file with volunteer
            return ResultVO.success("File uploaded successfully with ID: " + fileModel.getId());
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVO.failure("File upload failed");
        }
    }

    /**
     * Endpoint to upload an avatar for a volunteer.
     * @param file The file to be uploaded.
     * @param filename The name to save the avatar as.
     * @param volunteerId The ID of the volunteer to associate the avatar with.
     * @return ResultVO containing success or failure message.
     */
    @PostMapping("/uploadAvatorForVolunteer")
    public ResultVO<String> uploadAvatorForVolunteer(@RequestParam("file") MultipartFile file,
                                                     @RequestParam("filename") String filename,
                                                     @RequestParam("volunteerId") Long volunteerId) {

        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }
        String cleanFilename = StringUtils.cleanPath(filename) + fileExtension; // Sanitize filename
        try {
            FileModel fileModel = new FileModel();
            fileModel.setFilename(cleanFilename);
            fileModel.setContentType(file.getContentType());
            fileModel.setData(file.getBytes()); // Convert file to byte array
            fileService.saveFile(fileModel); // Save file to database
            userService.updateUserAvator(volunteerId, "/files/" + fileModel.getId()); // Update volunteer avatar
            return ResultVO.success("Avatar uploaded successfully with ID: " + fileModel.getId());
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVO.failure("Avatar upload failed");
        }
    }

    /**
     * Endpoint to retrieve a file by its ID.
     * @param id The ID of the file.
     * @return ResponseEntity containing the file data encoded in Base64 format.
     */
    @GetMapping("/files/{id}")
    public ResponseEntity<String> getFile(@PathVariable Long id) {
        FileModel fileModel = fileService.getFileById(id); // Retrieve file from the database
        if (fileModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if file not found
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        String base64Data = Base64Utils.encodeToString(fileModel.getData()); // Encode file data to Base64 format

        return new ResponseEntity<>(base64Data, headers, HttpStatus.OK); // Return file data with 200 OK status
    }
}
