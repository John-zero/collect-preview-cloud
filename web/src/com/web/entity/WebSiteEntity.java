package com.web.entity;

import java.io.Serializable;

public class WebSiteEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * FolderEntity
     */
    private Long folderId;

    /**
     * UserEntity
     */
    private Long userId;

    /**
     * BookMarkEntity
     */
    private Long bookMarkId;

    public WebSiteEntity ()
    {

    }

    public WebSiteEntity (long folderId, long userId, long bookMarkId)
    {
        this.folderId = folderId;
        this.userId = userId;
        this.bookMarkId = bookMarkId;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setFolderId(Long folderId)
    {
        this.folderId = folderId;
    }

    public Long getFolderId()
    {
        return folderId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setBookMarkId(Long bookMarkId)
    {
        this.bookMarkId = bookMarkId;
    }

    public Long getBookMarkId()
    {
        return bookMarkId;
    }

}
