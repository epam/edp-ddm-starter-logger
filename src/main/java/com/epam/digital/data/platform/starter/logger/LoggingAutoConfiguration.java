package com.epam.digital.data.platform.starter.logger;

import com.epam.digital.data.platform.starter.logger.aspect.LoggerAspect;
import com.epam.digital.data.platform.starter.logger.config.RequestLoggingFilterConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RequestLoggingFilterConfiguration.class)
@ComponentScan(basePackageClasses = LoggerAspect.class)
public class LoggingAutoConfiguration {

}
