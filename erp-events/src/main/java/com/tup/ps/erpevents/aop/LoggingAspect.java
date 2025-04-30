package com.tup.ps.erpevents.aop;

import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Aspect for logging exceptions thrown by
 * methods in the service implementation package.
 */
@Aspect
@Component
@AllArgsConstructor
public class LoggingAspect {

    /**
     * Logger for logging aspect.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logs an error message when an exception is thrown
     * by any method in the service implementation package.
     *
     * @param exception the exception that was thrown
     */
    @AfterThrowing(
            //pointcut = "execution(* com.tup.ps.erpevents.services.impl.*.*(..))",
            pointcut = "@within(org.springframework.stereotype.Service)",
            throwing = "exception"
    )
    public void logAfterThrowingServices(Exception exception) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LOGGER.error("⛔ Events-ERP | Services  [{}]: {}", timestamp, exception.getMessage(), exception);
    }

    /**
     * Logs an error message when an exception is thrown
     * by any method in the controller package.
     *
     * @param exception the exception that was thrown
     */
    @AfterThrowing(
            //pointcut = "execution(* com.tup.ps.erpevents.controllers.*.*(..))",
            pointcut = "@within(org.springframework.web.bind.annotation.RestController)",
            throwing = "exception"
    )
    public void logAfterThrowingInControllers(Exception exception) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LOGGER.error("⛔ Events-ERP | Controllers [{}]: {}", timestamp, exception.getMessage(), exception);
    }

    /**
     * Logs an error message when an exception is thrown
     * by any method in the application.
     *
     * @param exception the exception that was thrown
     */
    /*@AfterThrowing(
            pointcut = "execution(* com.tup.ps.erpevents..*(..)) "
                    + "&& !execution(* com.tup.ps.erpevents.services.impl.*.*(..))"
                    + "&& !execution(* com.tup.ps.erpevents.controllers.*.*(..))",
            throwing = "exception"
    )
    public void logAfterThrowing(Exception exception) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LOGGER.error("Events-ERP [{}]: {}", timestamp, exception.getMessage(), exception);
    }*/
}
