package com.epam.digital.data.platform.starter.logger.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

@Aspect
@Component
@ConditionalOnClass(name = "org.springframework.kafka.annotation.KafkaListener")
@Deprecated
public class KafkaListenerLoggerAspect extends AbstractLogger {

  @Around("@annotation(org.springframework.kafka.annotation.KafkaListener)")
  private Object logKafkaApi(ProceedingJoinPoint joinPoint) throws Throwable {
    return logJoinPoint(joinPoint);
  }
}
