package com.web.entity;

import com.web.enums.MacroTypeEnum;
import com.web.enums.MicroTypeEnum;

import java.io.Serializable;

public class BookMarkEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 宏分类
     */
    private MacroTypeEnum macroTypeEnum;

    /**
     * 细分类
     */
    private MicroTypeEnum microTypeEnum;

    /**
     * 站点
     */
    private String webSite;

    private String href;

    private String paginationHref;

    private String icon;

    public BookMarkEntity ()
    {

    }

    public BookMarkEntity (String webSite, String href, String icon)
    {
        this.webSite = webSite;
        this.href = href;
        this.icon = icon;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public MacroTypeEnum getMacroTypeEnum()
    {
        return macroTypeEnum;
    }

    public void setMacroTypeEnum(MacroTypeEnum macroTypeEnum)
    {
        this.macroTypeEnum = macroTypeEnum;
    }

    public MicroTypeEnum getMicroTypeEnum()
    {
        return microTypeEnum;
    }

    public void setMicroTypeEnum(MicroTypeEnum microTypeEnum)
    {
        this.microTypeEnum = microTypeEnum;
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

    public String getPaginationHref()
    {
        return paginationHref;
    }

    public void setPaginationHref(String paginationHref)
    {
        this.paginationHref = paginationHref;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

}
