package com.web.request_params_vo;

public class LoginVo
{
    private String userName;
    private String passWord;

    public LoginVo()
    {
        super();
    }

    public LoginVo(String userName, String passWord)
    {
        super();
        this.passWord = passWord;
        this.userName = userName;
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

}
