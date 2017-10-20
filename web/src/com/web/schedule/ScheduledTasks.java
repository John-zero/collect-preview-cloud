package com.web.schedule;

import com.aop.log.Log;
import com.aop.method.Method;
import com.web.entity.BookMarkEntity;
import com.web.service.IArticlesService;
import com.web.service.IBookMarksService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks
{
    private static final Logger LOG = LogManager.getLogger(ScheduledTasks.class);

    private static final String [] DOMAIN_NAMES = {"com", "cn", "net", "so", "org", "io", "tv", "info"};

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IBookMarksService bookMarksService;

    @Autowired
    private IArticlesService articlesService;

    @Scheduled(cron = "* 5,10,15,20,25,30,35,40,45,50,55 0-23 1-31 1-12 ?")
    @Log(description = "爬虫定时任务")
    @Method(description = "爬虫定时任务")
    public void reptile()
    {
        List<BookMarkEntity> bookMarks = bookMarksService.getAll();
        if(bookMarks == null || bookMarks.isEmpty())
        {
            LOG.info("没有任何 BookMarks 需要爬取...");
            return;
        }

        for(BookMarkEntity bookMarkEntity : bookMarks)
        {
            try
            {
                String webSite = bookMarkEntity.getWebSite();

                String webSiteStart = "http://";
                if(webSite.indexOf("https") > -1)
                    webSiteStart = "https://";

                String domainName = ".com";
                for(String _domainName : DOMAIN_NAMES)
                {
                    if(webSite.indexOf(_domainName) > -1)
                    {
                        domainName = "\\." + _domainName;
                        break;
                    }
                }

                String secondLevelDomain = webSite.substring(webSite.indexOf(webSiteStart + webSiteStart.length()), webSite.indexOf(domainName + domainName.length()));
                LOG.info(String.format("%s 识别二级域名为 %s", webSite, secondLevelDomain));


            }
            catch(Exception e)
            {

            }
        }
    }


    @Scheduled(cron = "* 5,10,15,20,25,30,35,40,45,50,55 0-23 1-31 1-12 ?")
    @Log(description = "Redis 内存分析定时任务")
    @Method(description = "Redis 内存分析定时任务")
    public void redisMemory ()
    {

    }

}
