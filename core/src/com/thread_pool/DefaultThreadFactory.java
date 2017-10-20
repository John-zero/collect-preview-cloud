package com.thread_pool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultThreadFactory implements ThreadFactory
{
    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final ThreadGroup threadGroup;
    private final String namePrefix;

    DefaultThreadFactory()
    {
        SecurityManager securityManager = System.getSecurityManager();
        threadGroup = (securityManager != null)? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = "pool-" +  POOL_NUMBER.getAndIncrement() +  "-thread-";
    }

    @Override
    public Thread newThread(Runnable runnable)
    {
        Thread thread = new Thread(threadGroup, runnable,namePrefix + threadNumber.getAndIncrement(),0);

        if (thread.isDaemon())
            thread.setDaemon(false);

        if (thread.getPriority() != Thread.NORM_PRIORITY)
            thread.setPriority(Thread.NORM_PRIORITY);

        return thread;
    }

}
