package com.munib.coding.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspect {
	
	private final Logger logger = LoggerFactory.getLogger(ServiceAspect.class);

	@Pointcut("@within(org.springframework.stereotype.Service)") 
//	@Pointcut("execution(* com.munib.coding.services.UserDetailsServiceImpl.*(..))")
	public void logExecutionTime() {}
	
	@AfterReturning("logExecutionTime()")
	public void logMethodExecution(JoinPoint jp)throws Throwable {
		String methodName = jp.getSignature().getName();
		String className = jp.getTarget().getClass().getSimpleName();
		
		logger.info("Request made to service = {}, in class = {}",methodName,className);
	}

}
