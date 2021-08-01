package com.epam.digital.data.platform.starter.logger.annotation;

import org.springframework.stereotype.Service;

@Service
public class ServiceMock {

  @Confidential
  public String methodServiceWithAnnotation() {
    return "checked information";
  }

  public String methodServiceWithOutAnnotation() {
    return "unchecked information";
  }

  public String methodServiceWithParameterAnnotation(@Confidential String id) {
    return id;
  }
}
