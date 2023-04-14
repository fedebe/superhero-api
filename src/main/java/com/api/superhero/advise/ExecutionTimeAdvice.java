package com.api.superhero.advise;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnExpression("${excecution.time.aspect.enabled:true}")
public class ExecutionTimeAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionTimeAdvice.class);
    
    private static String templateLog = "Execution time: %sms for %s. Package: %s";

    @Around("@annotation(com.api.superhero.advise.TrackExecutionTime)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {

        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        long endtime = System.currentTimeMillis();

                
        LOGGER.info(templateLog.formatted((endtime - startTime),
                point.getSignature().toShortString(),
                point.getSignature().getDeclaringType().getPackageName()));
        return object;
    }

}
