package com.log.converter;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.convert.TypeConverters;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.util.PerformanceSensitive;

/**
 * http://logging.apache.org/log4j/2.x/manual/plugins.html
 */
@Plugin(name = "JSONPatternConverter", category = TypeConverters.CATEGORY)
@ConverterKeys({"Application", "application"})
@PerformanceSensitive({"allocation"})
public class JSONPatternConverter extends LogEventPatternConverter
{

    private static final JSONPatternConverter INSTANCE = new JSONPatternConverter();

    public static JSONPatternConverter newInstance(final String[] options)
    {
        return INSTANCE;
    }

    private JSONPatternConverter()
    {
        super("Application", "application");
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo)
    {
        toAppendTo.append("John_zero");
    }

}
