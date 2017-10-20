package com.config;

import com.enums.EnvEnum;
import com.util.PropertiesUtil;

import java.util.Properties;

public class CoreConfig
{

    public static EnvEnum env ()
    {
        Properties properties = PropertiesUtil.properties("core.properties");

        return EnvEnum.gain(properties.getProperty("env"));
    }

}
