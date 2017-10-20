package com.web.entity;

import java.io.Serializable;

public class ArticlesEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Long folderId;

    /**
     * FolderEntity
     */
    private Long parentFolderId;

    /**
     * UserEntity
     */
    private Long userId;

    private String folderName;

    public ArticlesEntity()
    {
        this.folderId = 0L;
        this.parentFolderId = 0L;
        this.userId = 0L;
    }

    public ArticlesEntity(long userId, String folderName)
    {
        this();

        this.userId = userId;
        this.folderName = folderName;
    }

    public ArticlesEntity(long parentFolderId, long userId, String folderName)
    {
        this();

        this.parentFolderId = parentFolderId;
        this.userId = userId;
        this.folderName = folderName;
    }

    public Long getFolderId()
    {
        return folderId;
    }

    public void setFolderId(Long folderId)
    {
        this.folderId = folderId;
    }

    public Long getParentFolderId()
    {
        return parentFolderId;
    }

    public void setParentFolderId(Long parentFolderId)
    {
        this.parentFolderId = parentFolderId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getFolderName()
    {
        return folderName;
    }

    public void setFolderName(String folderName)
    {
        this.folderName = folderName;
    }

}
