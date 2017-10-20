package com.aop.method;

import com.util.TimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class MethodAOPAdvice
{

	private static final Logger LOG = LogManager.getLogger(MethodAOPAdvice.class);

	/**
	 * 环绕通知
	 */
	@Around("within(com.*..*) && @annotation(method)")
	public Object around (ProceedingJoinPoint proceedingJoinPoint, Method method) throws Throwable
	{
		long startNanoTime = System.nanoTime();

		Object object = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());

		LOG.info(String.format("环绕通知, 执行: %s, 耗时: %s 纳秒", method.description(), TimeUtil.consumeTimeByNoneTime(startNanoTime)));

		return object;
	}
	
}
