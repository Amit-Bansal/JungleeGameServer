package com.junglee.gameserver.application;

import org.springframework.beans.BeansException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppContext
{	
	private static ConfigurableApplicationContext applicationContext;

	public static void loadApplicationContext(String appContext) throws BeansException
	{
		AppContext.applicationContext = new ClassPathXmlApplicationContext(appContext);
	}
	
	public static Object getBean(String beanName)
	{
		if (null == beanName)
		{
			return null;
		}
		return applicationContext.getBean(beanName);
	}
		
}
