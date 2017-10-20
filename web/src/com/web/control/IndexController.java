package com.web.control;

import com.alibaba.fastjson.JSONObject;
import com.aop.log.Log;
import com.aop.method.Method;
import com.swagger.Error_400;
import com.swagger.Error_404;
import com.web.common.Constants;
import com.web.entity.UserEntity;
import com.web.mapper.UserMapper;
import com.web.request_params_vo.LoginVo;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController extends BaseController
{
    private static final Logger LOG = LogManager.getLogger(IndexController.class);

    @Autowired
    private UserMapper userMapper;


    @RequestMapping("/")
    @Log(description = "首页")
    @Method(description = "首页")
    @ApiOperation(value = "首页", notes = "首页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "device", value = "访问方式", required = true, dataType = "org.springframework.mobile.device.Device"),
            @ApiImplicitParam (name = "userAgent", value = "User-Agent", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "缺少参数", response = Error_400.class),
            @ApiResponse(code = 404, message = "URL 不对", response = Error_404.class)
    })
    public String index (Device device,
                         @RequestHeader(value="Accept") String accept,
                         @RequestHeader(value="Accept-Language") String acceptLanguage,
                         @RequestHeader(value="User-Agent") String userAgent,
                         HttpSession session)
    {
        LOG.info("访问方式： " + device.toString());
        LOG.info("访问 IP： " + getUserIp());
        LOG.info("Session： " + getSession().getId());
        LOG.info("Accept： " + accept);
        LOG.info("Accept-Language： " + acceptLanguage);
        LOG.info("User-Agent： " + userAgent);

        return "index";
    }

    @RequestMapping("/index")
    @Log(description = "首页")
    @Method(description = "首页")
    public String index (Device device)
    {
        return "index";
    }

    @RequestMapping("/_login")
    @Log(description = "登录页")
    @Method(description = "登录页")
    public String _login ()
    {
        return "login";
    }

    @PostMapping("/login")
    @Log(description = "登录")
    @Method(description = "登录")
    @ResponseBody
    public JSONObject login (@RequestBody LoginVo loginVo, HttpServletResponse response)
    {
        JSONObject result = new JSONObject();

        UserEntity userEntity = userMapper.getUser(loginVo.getUserName());
        if(userEntity == null)
        {
            result.put("result", false);
            result.put("message", "账号或者密码错误...");
        }
        else if(loginVo.getPassWord() == null || !loginVo.getPassWord().equals(userEntity.getPassWord()))
        {
            result.put("result", false);
            result.put("message", "账号或者密码错误...");
        }
        else
        {
            loginUser(response, userEntity);

            result.put("result", true);
            result.put("redirect_url", "/index");
        }

        return result;
    }

    @RequestMapping("/_register")
    @Log(description = "注册页")
    @Method(description = "注册页")
    public String _register ()
    {
        return "register";
    }

    @PostMapping("/check_account")
    @Log(description = "检查账号是否存在")
    @Method(description = "检查账号是否存在")
    @ResponseBody
    public JSONObject checkAccount (@RequestParam(value = "userName") String userName)
    {
        JSONObject result = new JSONObject();

        UserEntity userEntity = userMapper.getUser(userName);
        if(userEntity != null)
        {
            result.put("result", false);
            result.put("message", "该账号已经存在");
        }
        else
        {
            result.put("result", true);
            result.put("message", "该账号可以注册");
        }

        return result;
    }

    @PostMapping("/register")
    @Log(description = "注册")
    @Method(description = "注册")
    @ResponseBody
    public JSONObject register (@RequestParam(value = "userName") String userName,
                                @RequestParam(value = "passWord") String passWord,
                                HttpServletResponse response)
    {
        JSONObject result = new JSONObject();

        if(userMapper.getUser(userName) != null)
        {
            result.put("result", false);
            result.put("message", "账号已经存在...");
        }
        else
        {
            try
            {
                UserEntity userEntity = new UserEntity(userName, passWord);
                userMapper.insert(userEntity);

                loginUser(response, userEntity);

                result.put("result", true);
                result.put("redirect_url", "/index");
            }
            catch (Exception e)
            {
                LOG.error(e);
                result.put("result", false);
                result.put("message", "注册, 服务器处理异常...");
            }
        }

        return result;
    }

    private void loginUser(HttpServletResponse response, UserEntity userEntity)
    {
        // Cookie
        Cookie cookie = new Cookie(Constants.LOGIN_USER_SESSION_KEY, userEntity.getId().toString());
        cookie.setMaxAge(Constants.COOKIE_TIMEOUT);
        cookie.setPath("/");
        response.addCookie(cookie);

        // Session
        getSession().setAttribute(Constants.LOGIN_USER_SESSION_KEY, userEntity);
    }

}
