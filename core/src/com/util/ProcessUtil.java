package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;

/**
 * InetAddress.getLocalHost().getHostName();
 * System.getProperties();
 * System.getenv();
 */
public class ProcessUtil
{
    private static final Logger LOG = LogManager.getLogger(ProcessUtil.class);

    private enum ProcessTypeEnum
    {
        /**
         * 进程名称
         */
        PROCESS_NAME,
        /**
         * PID
         */
        PID,
        /**
         * 主机名称
         */
        HOST_NAME,
        /**
         * 主类
         */
        MAIN_CLASS
    }

    private static final Map<ProcessTypeEnum, Object> ATTRIBUTE = new HashMap<>();

    static
    {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String processName = runtime.getName();

        ATTRIBUTE.put(ProcessTypeEnum.PROCESS_NAME, processName);

        int index = processName.indexOf("@");
        if(index != -1)
        {
            ATTRIBUTE.put(ProcessTypeEnum.PID, Integer.parseInt(processName.substring(0, index)));
            ATTRIBUTE.put(ProcessTypeEnum.HOST_NAME, processName.substring(index + 1));
        }

        // For Oracle JDK/JRE
        String oracleJava = System.getProperty("sun.java.command");
        if (oracleJava != null && !"".equals(oracleJava))
        {
            ATTRIBUTE.put(ProcessTypeEnum.MAIN_CLASS, oracleJava);
        }
        else
        {
            for (final Map.Entry<String, String> entry : System.getenv().entrySet())
            {
                if (entry.getKey().startsWith("JAVA_MAIN_CLASS"))
                    ATTRIBUTE.put(ProcessTypeEnum.MAIN_CLASS, entry.getValue());
            }
        }
    }

    public static String gainProcessName ()
    {
        if(ATTRIBUTE.containsKey(ProcessTypeEnum.PROCESS_NAME))
            return (String) ATTRIBUTE.get(ProcessTypeEnum.PROCESS_NAME);
        else
            return "";
    }

    public static int gainPID ()
    {
        if(ATTRIBUTE.containsKey(ProcessTypeEnum.PID))
            return (int) ATTRIBUTE.get(ProcessTypeEnum.PID);
        else
            return -1;
    }

    public static String gainHostName ()
    {
        if(ATTRIBUTE.containsKey(ProcessTypeEnum.HOST_NAME))
            return (String) ATTRIBUTE.get(ProcessTypeEnum.HOST_NAME);
        else
            return "";
    }

    public static String gainMainClass ()
    {
        if(ATTRIBUTE.containsKey(ProcessTypeEnum.MAIN_CLASS))
            return (String) ATTRIBUTE.get(ProcessTypeEnum.MAIN_CLASS);
        else
            return "";
    }

}
