package com.epam.digital.data.platform.starter.logger.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * The class represents a configuration for {@link CommonsRequestLoggingFilter} filter. Also, this
 * logging filter requires the logging level be set to DEBUG.
 * <p>
 * This bean will be created in case when logging level for CommonsRequestLoggingFilter is set to
 * debug.
 */
@Configuration
public class RequestLoggingFilterConfiguration {

  @ConditionalOnProperty(name = "logging.level.org.springframework.web.filter.CommonsRequestLoggingFilter", havingValue = "DEBUG")
  @Bean
  public CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeQueryString(true);
    filter.setIncludeHeaders(true);
    filter.setIncludePayload(true);
    filter.setMaxPayloadLength(100000);
    filter.setAfterMessagePrefix("REQUEST DATA : ");
    return filter;
  }
}
