package com.epam.digital.data.platform.starter.logger.aspect;

import com.epam.digital.data.platform.starter.logger.annotation.Confidential;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

@Slf4j
@Deprecated
public abstract class AbstractLogger {

  protected Object logJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature methodSig = (MethodSignature) joinPoint.getSignature();
    Method method = methodSig.getMethod();
    logArguments(joinPoint, method);
    Object proceed = joinPoint.proceed();
    logResult(proceed, method);
    return proceed;
  }

  private void logArguments(ProceedingJoinPoint joinPoint, Method method) {
    final var className = joinPoint.getTarget().getClass().getName();
    final var methodName = method.getName();
    final var arguments = joinPoint.getArgs();
    final var checkedArguments = getCheckedArguments(method, arguments);
    log.info("{} invoke {} with args {}", className, methodName, checkedArguments);
  }

  private void logResult(Object proceed, Method method) {
    if (isConfidentialElement(method)) {
      log.info("Invoked result confidential information");
    } else {
      log.info("Invoked result {}", proceed);
    }
  }

  private List<Object> getCheckedArguments(Method method, Object[] arguments) {
    final var checkedArguments = new ArrayList<>();
    Parameter[] parameters = method.getParameters();
    for (int i = 0; i < parameters.length; i++) {
      checkedArguments.add(sanitize(parameters[i], arguments[i]));
    }
    return checkedArguments;
  }

  private Object sanitize(Parameter parameter, Object argument) {
    return isConfidentialElement(parameter) ? "confidential information" : argument;
  }

  private boolean isConfidentialElement(AnnotatedElement annotatedElement) {
    return annotatedElement.isAnnotationPresent(Confidential.class);
  }
}
