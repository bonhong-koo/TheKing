package com.pcwk.ehr.cmn.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;

import com.pcwk.ehr.cmn.PLog;

public class PerformanceAdvice implements PLog {
	
	public Object logExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
		Object retObj = null;
		long start = System.currentTimeMillis();

		log.debug("┌─────────────────────────┐");
		log.debug("│ *aroundLog()*           │");
		//클래스 명
		String className = pjp.getTarget().getClass().getName();
		
		//메서드 명
		String methodName = pjp.getSignature().getName();
		
		//대상 메서드 실행
		retObj = pjp.proceed();
		log.debug("│ className               │"+className);
		log.debug("│ methodName              │"+methodName);
		log.debug("└─────────────────────────┘");

		long end = System.currentTimeMillis();
		long result = end-start;
		
		log.debug("^^^^^^ExecutionTime : " + result);
		
		return retObj;
	}
}
