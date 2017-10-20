package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;

/**
 * 反射工具类
 * @author John_zero
 */
public final class ReflectUtil
{
	private static final Logger LOG = LogManager.getLogger(ReflectUtil.class);

	private ReflectUtil()
	{
		
	}

	/**
	 * 无参构造函数创建类
	 * @param clazz
	 * @return
	 */
	public static Object constructor (Class<?> clazz)
	{
		try 
		{
			// 方法传入的参数类型
			Class<?> [] paramTypes = { };
			// 方法传入的参数
			Object [] params = { }; 
			
			return constructor(clazz, paramTypes, params);
		}
		catch (Exception e) 
		{
			LOG.error(String.format("通过无参构造函数反射创建类时出现异常, class: %s", clazz), e);
		} 
		return null;
	}
	
	/**
	 * 有参构造函数创建类
	 * @param clazz
	 * @param paramTypes
	 * @param params
	 * @return
	 */
	public static Object constructor (Class<?> clazz, Class<?> [] paramTypes, Object [] params)
	{
		try 
		{
			Constructor<?> constructor = clazz.getConstructor(paramTypes); 
			
			return constructor.newInstance(params);
		}
		catch (Exception e) 
		{
			LOG.error(String.format("通过有参构造函数反射创建类时出现异常, class: %s", clazz), e);
		} 
		return null;
	}

}
