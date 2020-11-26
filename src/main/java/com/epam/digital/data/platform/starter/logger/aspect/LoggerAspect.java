/*
 * Copyright 2021 EPAM Systems.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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