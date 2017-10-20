package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil
{
    private static final Logger LOG = LogManager.getLogger(PropertiesUtil.class);

    public static Properties properties (String fileName)
    {
        try (InputStream inputStream = new BufferedInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));)
        {
            Properties properties = new Properties();

            properties.load(inputStream);

            return properties;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LOG.error(e);

            return null;
        }
    }

}
