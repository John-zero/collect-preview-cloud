package com.web.entity;

import java.io.Serializable;

public class UserEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Long id;
    private String userName;
    private String passWord;
    private String email;

    public UserEntity()
    {
        super();
    }

    public UserEntity(String userName, String passWord)
    {
        super();
        this.passWord = passWord;
        this.userName = userName;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassWord()
    {
        return passWord;
    }

    public void setPassWord(String passWord)
    {
        this.passWord = passWord;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

}
