package com.web.mapper;

import com.web.entity.WebSiteEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WebSitesMapper
{

    long insert(WebSiteEntity webSiteEntity);

    void clear (@Param("userId") long userId);

    List<WebSiteEntity> getAllByFolderId (@Param("folderId") long folderId);

}
