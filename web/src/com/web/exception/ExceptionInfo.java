package com.web.exception;

public class ExceptionInfo<T>
{
    private String code;

    private String desc;

    private String url;

    private T data;

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public String getDesc ()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public T getData ()
    {
        return data;
    }

    public void setData (T data)
    {
        this.data = data;
    }

}
