package com.wsa.mapper;

import com.wsa.model.FileModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {

    void insert(FileModel fileModel);

    FileModel findById(Long id);
}
