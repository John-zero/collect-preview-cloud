package com.web.control;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseController
{
    private static final Logger LOG = LogManager.getLogger(IndexController.class);

    protected HttpServletRequest getRequest()
    {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    protected HttpSession getSession()
    {
        return getRequest().getSession();
    }

    protected String getUserIp()
    {
        String ip = getRequest().getHeader("X-Real-IP");
        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip))
            return ip;
        else
            return getRequest().getRemoteAddr();
    }

}
