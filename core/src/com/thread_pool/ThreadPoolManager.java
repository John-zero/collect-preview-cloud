package com.thread_pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

public class ThreadPoolManager
{
    private static final Logger LOG = LogManager.getLogger(ThreadPoolManager.class);

    private ThreadPoolExecutor threadPoolExecutor;

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    private ThreadPoolManager ()
    {
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 20 - 1;
        int maximumPoolSize = Integer.MAX_VALUE;
        long keepAliveTime = 60L;
        TimeUnit milliseconds = TimeUnit.SECONDS;
        BlockingQueue<Runnable> runnableTaskQueue = new SynchronousQueue<>(); // 默认, 同步阻塞队列
        ThreadFactory threadFactory = new DefaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy(); // 默认, 饱和则抛出异常

        this.threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, milliseconds, runnableTaskQueue, threadFactory, handler);

        this.scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(corePoolSize, threadFactory, handler);
    }

    public static ThreadPoolManager getInstance()
    {
        return SingletonHolder.INSTALL;
    }

    private static class SingletonHolder
    {
        private final static ThreadPoolManager INSTALL = new ThreadPoolManager ();
    }

    public void execute(Runnable runnable)
    {
        this.threadPoolExecutor.execute(runnable);
    }

    public Future<?> submit(Runnable runable)
    {
        return this.threadPoolExecutor.submit(runable);
    }

    /**
     * @param runnable
     * @param period 单位: 毫秒
     * @return
     */
    public ScheduledFuture scheduleAtFixedRate(Runnable runnable, long period)
    {
        return this.scheduledThreadPoolExecutor.scheduleAtFixedRate(runnable, 0, period, TimeUnit.MILLISECONDS);
    }

    /**
     * @param runnable
     * @param initialDelay
     * @param period
     * @param timeUnit
     * @return
     */
    public ScheduledFuture scheduleAtFixedRate(Runnable runnable, long initialDelay, long period, TimeUnit timeUnit)
    {
        return this.scheduledThreadPoolExecutor.scheduleAtFixedRate(runnable, initialDelay, period, timeUnit);
    }

    /**
     * @param runnable
     * @param period 单位: 毫秒
     * @return
     */
    public ScheduledFuture scheduleWithFixedDelay(Runnable runnable, long period)
    {
        return this.scheduledThreadPoolExecutor.scheduleWithFixedDelay(runnable, 0, period, TimeUnit.MILLISECONDS);
    }

    /**
     * @param runnable
     * @param initialDelay
     * @param period
     * @param timeUnit
     * @return
     */
    public ScheduledFuture scheduleWithFixedDelay(Runnable runnable, long initialDelay, long period, TimeUnit timeUnit)
    {
        return this.scheduledThreadPoolExecutor.scheduleWithFixedDelay(runnable, initialDelay, period, timeUnit);
    }

    /**
     * 监控
     */
    public void monitoring ()
    {
        LOG.info(String.format("监控测试项目普通任务线程池, CorePoolSize: %s, MaximumPoolSize: %s, PoolSize: %s, ActiveCount: %s, CompletedTaskCount: %s, TaskCount: %s",
                this.threadPoolExecutor.getCorePoolSize(), this.threadPoolExecutor.getMaximumPoolSize(), this.threadPoolExecutor.getPoolSize(),
                this.threadPoolExecutor.getActiveCount(), this.threadPoolExecutor.getCompletedTaskCount(), this.threadPoolExecutor.getTaskCount()));

        LOG.info(String.format("监控测试项目定时任务线程池, CorePoolSize: %s, MaximumPoolSize: %s, PoolSize: %s, ActiveCount: %s, CompletedTaskCount: %s, TaskCount: %s",
                this.scheduledThreadPoolExecutor.getCorePoolSize(), this.scheduledThreadPoolExecutor.getMaximumPoolSize(), this.scheduledThreadPoolExecutor.getPoolSize(),
                this.scheduledThreadPoolExecutor.getActiveCount(), this.scheduledThreadPoolExecutor.getCompletedTaskCount(), this.scheduledThreadPoolExecutor.getTaskCount()));
    }

}
