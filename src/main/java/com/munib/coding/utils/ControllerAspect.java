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
public class ControllerAspect {
	
	private final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);
//	
	@Pointcut("execution(* com.munib.coding.controller.UserController.*(..))")
	public void controllerLogExecution() {
	};
	
	@AfterReturning(pointcut = "controllerLogExecution()", returning = "result")
	public void logMehtodExecutoin(JoinPoint jp, Object result) {
		String methodName = jp.getSignature().getName();
		String className = jp.getTarget().getClass().getSimpleName();
		logger.info("Method = {} from class = {} executed successfully. ", methodName,className);
		logger.info("Method result = {}",result);
	};
//

//	 @AfterReturning(pointcut = "execution(* com.munib.coding.controller.UserController.*(..))", returning = "result")
//	    public void logAfterMethodExecution(JoinPoint joinPoint, Object result) {
//	        String className = joinPoint.getTarget().getClass().getSimpleName();
//	        String methodName = joinPoint.getSignature().getName();
//
//	        logger.info("Method {} from class {} returned with result: {}", methodName, className, result);
//	 };
	 
	 
}
	        
	        
	   
