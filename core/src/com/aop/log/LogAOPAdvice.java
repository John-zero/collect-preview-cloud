package com.aop.log;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

/**
 * Demo: https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples/spring-boot-sample-aop
 * Docs: https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html
 */
@Aspect
@Service
public class LogAOPAdvice
{

	private static final Logger LOG = LogManager.getLogger(LogAOPAdvice.class);

	/**
	 * 前置通知
	 */
	@Before("within(com.web..*) && @annotation(log)")
	public void before (JoinPoint joinPoint, Log log)
	{
		LOG.info(String.format("前置通知, 开始执行: %s", log.description()));
		LOG.info(String.format("前置通知, 执行函数: %s", joinPoint.getSignature().toString()));

		Object [] args = joinPoint.getArgs();
		if(args != null && args.length > 0)
		{
			StringBuffer parameter = new StringBuffer("前置通知, 入参: ");
			for (Object obj : args)
				parameter.append(ToStringBuilder.reflectionToString(obj)).append("  ");

			LOG.info(parameter.toString());
		}
	}

	/**
	 * 后置通知
	 */
	@Before("within(com.web..*) && @annotation(log)")
	public void after (JoinPoint joinPoint, Log log)
	{
		LOG.info(String.format("后置通知, 完成执行: %s", log.description()));
	}

	/**
	 * 返回通知
	 */
	@AfterReturning(value = "within(com.web..*) && @annotation(log)", returning = "returnValue")
	public void afterReturning (JoinPoint joinPoint, Log log, Object returnValue)
	{
		LOG.info(String.format("返回通知, 执行: %s, 返回: %s", log.description(), ToStringBuilder.reflectionToString(returnValue)));
	}

	/**
	 * 异常通知
	 */
	@AfterThrowing(pointcut = "within(com.web..*) && @annotation(log)", throwing = "ex")
	public void afterThrowing (JoinPoint joinPoint, Log log, Exception ex)
	{
		LOG.error(String.format("异常通知, 执行 %s 异常", log.description()), ex);
	}
	
}
