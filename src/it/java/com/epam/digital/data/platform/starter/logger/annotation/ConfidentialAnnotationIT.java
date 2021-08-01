package com.epam.digital.data.platform.starter.logger.annotation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import com.epam.digital.data.platform.starter.logger.LoggingAutoConfiguration;
import com.epam.digital.data.platform.starter.logger.aspect.AbstractLogger;
import com.epam.digital.data.platform.starter.logger.aspect.LoggerAspect;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PowerMockIgnore({"javax.management.*", "javax.script.*"})
@PrepareForTest({LoggerAspect.class, LoggerFactory.class})
@ContextConfiguration(classes = LoggingAutoConfiguration.class,
    initializers = ConfigFileApplicationContextInitializer.class)
public class ConfidentialAnnotationIT {

  private static Logger loggerMock;
  private static ServiceMock proxyServiceMock;

  @BeforeClass
  public static void classSetup() {
    loggerMock = mock(Logger.class);
    mockStatic(LoggerFactory.class);

    when(LoggerFactory.getLogger(AbstractLogger.class)).thenReturn(loggerMock);

    ServiceMock target = new ServiceMock();
    AspectJProxyFactory factory = new AspectJProxyFactory(target);
    AbstractLogger aspect = new LoggerAspect();
    factory.addAspect(aspect);
    proxyServiceMock = factory.getProxy();
  }

  @Test
  public void shouldLogConfidentialResultAndEmptyArguments() {
    var expected = "checked information";
    var actual = proxyServiceMock.methodServiceWithAnnotation();

    assertEquals(expected, actual);
    verify(loggerMock).info("{} invoke {} with args {}",
        "com.epam.digital.data.platform.starter.logger.annotation.ServiceMock",
        "methodServiceWithAnnotation", new ArrayList<>());
    verify(loggerMock).info("Invoked result confidential information");
  }

  @Test
  public void shouldLogResultAndEmptyArguments() {
    var expected = "unchecked information";
    var actual = proxyServiceMock.methodServiceWithOutAnnotation();

    assertEquals(expected, actual);
    verify(loggerMock).info("{} invoke {} with args {}",
        "com.epam.digital.data.platform.starter.logger.annotation.ServiceMock",
        "methodServiceWithOutAnnotation", new ArrayList<>());
    verify(loggerMock).info("Invoked result {}", expected);
  }

  @Test
  public void shouldLogResultAndConfidentialArguments() {
    var expected = "secret id";
    var actual = proxyServiceMock.methodServiceWithParameterAnnotation(expected);

    assertEquals(expected, actual);
    verify(loggerMock).info("{} invoke {} with args {}",
        "com.epam.digital.data.platform.starter.logger.annotation.ServiceMock",
        "methodServiceWithParameterAnnotation",
        Collections.singletonList("confidential information"));
    verify(loggerMock).info("Invoked result {}", expected);
  }
}
