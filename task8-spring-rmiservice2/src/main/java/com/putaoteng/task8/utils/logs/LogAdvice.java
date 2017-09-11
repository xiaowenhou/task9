package com.putaoteng.task8.utils.logs;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/*@Component (value="logAdvice")
@Aspect
public class LogAdvice {
	private static Logger logger = Logger.getLogger(LogAdvice.class.getName());

	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();

		Object obj = pjp.proceed();

		long endTime = System.currentTimeMillis();
		long time = endTime - startTime;
		logger.warn(pjp.getSignature() + " " + time);

		return obj;
	}
}*/
