package com.peregud.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionLoggingAspect.class);

    @AfterThrowing(pointcut = "within(@org.springframework.web.bind.annotation.RestController *)", throwing = "ex")
    public void logRestControllerExceptions(JoinPoint joinPoint, Exception ex) {
        logger.error("Exception in RestController {}.{}() with cause = {}",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            ex.getCause() != null ? ex.getCause() : "NULL");
    }

    @AfterThrowing(pointcut = "within(@org.springframework.stereotype.Service *)", throwing = "ex")
    public void logServiceExceptions(JoinPoint joinPoint, Exception ex) {
        logger.error("Exception in Service {}.{}() with cause = {}",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            ex.getCause() != null ? ex.getCause() : "NULL");
    }

    @AfterThrowing(pointcut = "within(@org.springframework.stereotype.Repository *)", throwing = "ex")
    public void logRepositoryExceptions(JoinPoint joinPoint, Exception ex) {
        logger.error("Exception in Repository {}.{}() with cause = {}",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            ex.getCause() != null ? ex.getCause() : "NULL");
    }
}
