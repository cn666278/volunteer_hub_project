package com.wsa.service;

import com.wsa.model.FileModel;
import com.wsa.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    public void saveFile(FileModel fileModel) {
        fileMapper.insert(fileModel);
    }

    public FileModel getFileById(Long id) {
        return fileMapper.findById(id);
    }
}
