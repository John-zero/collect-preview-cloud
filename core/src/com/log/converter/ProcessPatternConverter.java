package com.log.converter;

import com.util.ProcessUtil;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.convert.TypeConverters;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.util.PerformanceSensitive;

/**
 * http://logging.apache.org/log4j/2.x/manual/plugins.html
 */
@Plugin(name = "ProcessPatternConverter", category = TypeConverters.CATEGORY)
@ConverterKeys({"P", "PID"})
@PerformanceSensitive({"allocation"})
public class ProcessPatternConverter extends LogEventPatternConverter
{

    private static final ProcessPatternConverter INSTANCE = new ProcessPatternConverter();

    public static ProcessPatternConverter newInstance(final String[] options)
    {
        return INSTANCE;
    }

    private ProcessPatternConverter()
    {
        super("PID", "pid");
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo)
    {
        toAppendTo.append(ProcessUtil.gainPID());
    }

}
