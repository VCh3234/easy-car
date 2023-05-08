package by.easycar.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AopLogger {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* by.easycar.*.*.*.*.*.* (..))")
    public void logBeforeMethods(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().toString();
        if (logger.isDebugEnabled()) {
            logger.info("Execute " + methodName);
            logger.debug("Args of " + methodName + ": " + Arrays.toString(args));
        } else {
            logger.info("Execute " + methodName);
        }
    }
}