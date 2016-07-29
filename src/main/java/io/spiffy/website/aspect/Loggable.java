package io.spiffy.website.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

import io.spiffy.common.Aspect;

@org.aspectj.lang.annotation.Aspect
public class Loggable extends Aspect {

    @Around("spiffy()")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        logger.info(pjp.getSignature().getName());
        return pjp.proceed();
    }
}
