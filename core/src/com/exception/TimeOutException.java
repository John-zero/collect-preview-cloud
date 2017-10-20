package com.exception;

public class TimeOutException extends RuntimeException
{

    public TimeOutException(String message)
    {
        super(message);
    }

    public TimeOutException(int timeOutInSecond)
    {
        super(String.format("超时: %s", timeOutInSecond));
    }

}
