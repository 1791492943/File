package com.example.mapper;

import com.example.domain.entity.FileInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UploadMapper {

    @Insert("insert into file_info " +
            "(original_name, final_name, file_path, file_path_name, size, suffix, upload_date, upload_user) " +
            "values " +
            "(#{originalName},#{finalName},#{filePath},#{filePathName},#{size},#{suffix},now(),#{uploadUser})"
    )
    int save(FileInfo fileInfo);

}
