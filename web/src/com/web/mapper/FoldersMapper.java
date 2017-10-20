package com.web.mapper;

import com.web.entity.FolderEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FoldersMapper
{

    void clear(@Param("userId") long userId);

    FolderEntity getByFolderName(@Param("folderName") String folderName);

    long insert(FolderEntity folderEntity);

    List<FolderEntity> getAllByUserId (@Param("userId") long userId);

}
