package com.log.appender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;

@Plugin(name = "FileAppender", category = "Core", elementType = "appender", printObject = true)
public class FileAppender extends AbstractAppender
{
    private static final Logger LOG = LogManager.getLogger(FileAppender.class);

    private String fileName;

    public FileAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, String fileName)
    {
        super(name, filter, layout, ignoreExceptions);

        this.fileName = fileName;
    }

    @Override
    public void append(LogEvent event)
    {
        final byte [] logs = getLayout().toByteArray(event);

        writeFile(logs);
    }

    /**
     * 接收配置文件中的参数
     * @param name
     * @param fileName
     * @param filter
     * @param layout
     * @param ignoreExceptions
     * @return
     */
    @PluginFactory
    public static FileAppender createAppender(@PluginAttribute("name") String name,
                                              @PluginAttribute("fileName") String fileName,
                                              @PluginElement("Filter") final Filter filter,
                                              @PluginElement("Layout") Layout<? extends Serializable> layout,
                                              @PluginAttribute("ignoreExceptions") boolean ignoreExceptions)
    {
        if (name == null)
        {
            LOG.error("no name defined in conf.");
            return null;
        }
        if (fileName == null)
        {
            LOG.error("no fileName defined in conf.");
            return null;
        }

        if (layout == null)
            layout = PatternLayout.createDefaultLayout();

        // 创建文件
        if (!createFile(fileName))
            return null;

        return new FileAppender(name, filter, layout, ignoreExceptions, fileName);
    }

    private static boolean createFile(String fileName)
    {

        return true;
    }

    private void writeFile (byte [] logs)
    {
        LOG.info("666, 执行自定义的 File Appender...");
    }

}
