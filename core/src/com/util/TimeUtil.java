package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * @author John_zero
 */
public final class TimeUtil
{
    private static final Logger LOG = LogManager.getLogger(TimeUtil.class);

    private TimeUtil()
    {

    }

    public static final String y_M_d = "yyyy-MM-dd";

    public static final String H_m_s = "HH:mm:ss";

    public static final String y_M_d_H_m_s = "yyyy-MM-dd HH:mm:ss";

    /**
     * 当前时间 (单位: 秒)
     * @return
     */
    public static long currentTimeForSecond ()
    {
        return System.currentTimeMillis() / 1000L;
    }

    /**
     * 将Long(毫秒)时间转换为字符串时间
     * @param dateTime 单位: 毫秒
     * @return
     */
    public static String parseToStr (long dateTime)
    {
        try
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.format(dateTime);
        }
        catch (Exception e)
        {
            LOG.error(String.format("将long(毫秒)时间(%s)转换为字符串时间出现异常", dateTime), e);
            return "";
        }
    }

    public static String parseToStr (Date date, String pattern)
    {
        try
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            LOG.error(e);
            return "";
        }
    }

    /**
     * 耗时 (单位: 纳秒)
     * @param startNanoTime
     * @return
     */
    public static long consumeTimeByNoneTime (long startNanoTime)
    {
        long endNanoTime = System.nanoTime();

        return ((endNanoTime - startNanoTime) / 1000000);
    }

}
