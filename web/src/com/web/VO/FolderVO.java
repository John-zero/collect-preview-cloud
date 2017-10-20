package com.web.VO;

import com.http.utils.book_mark.BookMarkNodeTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class FolderVO
{

    private Long folderId;
    private String folderName;

    private Long parentFolderId;
    private FolderVO parentFolderVO;

    private BookMarkNodeTypeEnum bookMarkNodeTypeEnum;

    private long webSiteId;

    private long bookMarkId;
    private String webSite;
    private String href;
    private String icon;

    private List<FolderVO> childrenFolders = new ArrayList<>(0);


    public Long getFolderId()
    {
        return folderId;
    }

    public void setFolderId(Long folderId)
    {
        this.folderId = folderId;
    }

    public String getFolderName()
    {
        return folderName;
    }

    public void setFolderName(String folderName)
    {
        this.folderName = folderName;
    }

    public Long getParentFolderId()
    {
        return parentFolderId;
    }

    public void setParentFolderId(Long parentFolderId)
    {
        this.parentFolderId = parentFolderId;
    }

    public BookMarkNodeTypeEnum getBookMarkNodeTypeEnum()
    {
        return bookMarkNodeTypeEnum;
    }

    public void setBookMarkNodeTypeEnum(BookMarkNodeTypeEnum bookMarkNodeTypeEnum)
    {
        this.bookMarkNodeTypeEnum = bookMarkNodeTypeEnum;
    }


    public FolderVO getParentFolderVO()
    {
        return parentFolderVO;
    }

    public void setParentFolderVO(FolderVO parentFolderVO)
    {
        this.parentFolderVO = parentFolderVO;
    }

    public long getWebSiteId()
    {
        return webSiteId;
    }

    public void setWebSiteId(long webSiteId)
    {
        this.webSiteId = webSiteId;
    }

    public long getBookMarkId()
    {
        return bookMarkId;
    }

    public void setBookMarkId(long bookMarkId)
    {
        this.bookMarkId = bookMarkId;
    }

    public String getWebSite()
    {
        return webSite;
    }

    public void setWebSite(String webSite)
    {
        this.webSite = webSite;
    }

    public String getHref()
    {
        return href;
    }

    public void setHref(String href)
    {
        this.href = href;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public List<FolderVO> getChildrenFolders()
    {
        return childrenFolders;
    }

    public void setChildrenFolders(List<FolderVO> childrenFolders)
    {
        this.childrenFolders = childrenFolders;
    }

}
