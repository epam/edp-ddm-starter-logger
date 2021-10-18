package com.epam.digital.data.platform.starter.logger.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Deprecated
public class LoggerAspect extends AbstractLogger {

  @Around("@within(com.epam.digital.data.platform.starter.logger.annotation.Logging) &&"
      + "execution(public !static * *.*(..))")
  public Object logAnnotated(ProceedingJoinPoint joinPoint) throws Throwable {
    return logJoinPoint(joinPoint);
  }

  @Around("@within(org.springframework.stereotype.Service) &&"
      + "execution(public !static * *.*(..))")
  private Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
    return logJoinPoint(joinPoint);
  }

  @Around("@within(org.springframework.web.bind.annotation.RestController) &&"
      + "execution(public !static * *.*(..)) &&"
      + "!execution(* org.springdoc..*.*(..))")
  public Object logRestController(ProceedingJoinPoint joinPoint) throws Throwable {
    return logJoinPoint(joinPoint);
  }
}