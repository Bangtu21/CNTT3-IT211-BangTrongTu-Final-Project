package spring_boot.it211projectfinal.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Around(
            "execution(* spring_boot.it211projectfinal.service.impl.*.*(..))"
    )
    public Object logExecutionTime(
            ProceedingJoinPoint joinPoint)
            throws Throwable {

        long startTime = System.currentTimeMillis();

        String methodName = joinPoint.getSignature().toShortString();

        log.info("START: {}", methodName);

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;

        log.info(
                "END: {} | Execution Time: {} ms",
                methodName,
                executionTime
        );

        return result;
    }

    @AfterThrowing(
            pointcut =
                    "execution(* spring_boot.it211projectfinal.service.impl.*.*(..))",
            throwing = "ex"
    )
    public void logException(Exception ex){

        log.error(
                "Exception: {}",
                ex.getMessage()
        );
    }

    @Before(
            "execution(* spring_boot.it211projectfinal.controller.*.*(..))"
    )
    public void beforeController(
            JoinPoint joinPoint){

        log.info(
                "API Called: {}",
                joinPoint.getSignature()
        );
    }
}
