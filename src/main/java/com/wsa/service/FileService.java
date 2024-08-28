package com.wsa.service;

import com.wsa.model.FileModel;
import com.wsa.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing file-related operations.
 * This service interacts with the data access layer (FileMapper) to handle file storage and retrieval.
 */
@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper; // Mapper for database interactions related to files

    /**
     * Saves a file to the database.
     * @param fileModel The file model containing file details to be saved.
     */
    public void saveFile(FileModel fileModel) {
        fileMapper.insert(fileModel); // Insert the file record into the database
    }

    /**
     * Retrieves a file from the database by its ID.
     * @param id The ID of the file to retrieve.
     * @return The FileModel object containing file details, or null if not found.
     */
    public FileModel getFileById(Long id) {
        return fileMapper.findById(id); // Find the file record by its ID
    }
}
