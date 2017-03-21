package elu.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil  implements ApplicationContextAware {

	@Autowired
	private static ApplicationContext context;
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		 SpringContextUtil.context=applicationContext;
	}
	
	public static <T> T getBean(String name, Class<T> requiredType){
		return context.getBean(name, requiredType);
	}
	
	public static Object getBean(String name){
		return context.getBean(name);
	}

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		SpringContextUtil.context = context;
	}
 
	
	
}
