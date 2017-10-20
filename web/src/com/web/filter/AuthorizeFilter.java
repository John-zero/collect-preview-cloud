package com.web.filter;

import com.web.common.Constants;
import com.web.entity.UserEntity;
import com.web.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthorizeFilter implements Filter
{
    private static final Logger LOG = LogManager.getLogger(AuthorizeFilter.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String uri = httpServletRequest.getRequestURI();

        LOG.info(String.format("过滤路径: %s", uri));

        if (httpServletRequest.getSession().getAttribute(Constants.LOGIN_USER_SESSION_KEY) == null)
        {
            if(resourcesSuffix(uri) || excludeUrl(uri))
            {
                chain.doFilter(request, response);
                return;
            }

            Cookie [] cookies = httpServletRequest.getCookies();
            if(cookies != null && cookies.length > 0)
            {
                for (int i = 0; i < cookies.length; i++)
                {
                    Cookie cookie = cookies[i];
                    if (cookie.getName().equals(Constants.LOGIN_USER_SESSION_KEY))
                    {
                        if (StringUtils.isNotBlank(cookie.getValue()))
                        {
                            String userId = cookie.getValue();

                            if (userMapper == null)
                            {
                                BeanFactory beanFactory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
                                userMapper = (UserMapper) beanFactory.getBean("userMapper");
                            }

                            UserEntity userEntity = userMapper.getById(Long.parseLong(userId));
                            if(userEntity != null)
                            {
                                LOG.info(String.format("%s 利用 Cookie 登录成功", userEntity.getUserName()));

                                httpServletRequest.getSession().setAttribute(Constants.LOGIN_USER_SESSION_KEY, userEntity);

                                chain.doFilter(request, response);

                                return;
                            }
                        }
                    }
                }
            }

            response.getWriter().write("<script type=\"text/javascript\">window.location.href=\"/_login\"</script>");
        }
        else
        {
            chain.doFilter(request, response);
        }
    }

    /**
     * 资源
     */
    private boolean resourcesSuffix (String url)
    {
        if (url.endsWith(".js")
                || url.endsWith(".css")
                || url.endsWith(".jpg")
                || url.endsWith(".gif")
                || url.endsWith(".png")
                || url.endsWith(".html")
                || url.endsWith(".ico")
        )
        {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 排除路径
     */
    private boolean excludeUrl (String url)
    {
        if ("/_login".contains(url) // 登录页
                || "/_register".contains(url) // 注册页
                || "/login".contains(url) // 登录请求
                || "/check_account".contains(url) // 注册验证账号是否存在请求
                || "/register".contains(url) // 注册请求
        )
        {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void destroy()
    {

    }

}
