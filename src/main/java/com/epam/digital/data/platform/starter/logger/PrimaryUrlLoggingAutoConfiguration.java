package com.epam.digital.data.platform.starter.logger;

import com.epam.digital.data.platform.starter.logger.filter.PrimaryUrlFilter;
import javax.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "platform.logging.primary-url", name = "enabled")
public class PrimaryUrlLoggingAutoConfiguration {

  @Bean
  public FilterRegistrationBean<PrimaryUrlFilter> primaryUrlFilter(
      @Value("${platform.logging.primary-url.filter-order:-2147483644}") Integer filterOrder) {
    var filterRegistrationBean = new FilterRegistrationBean<>(new PrimaryUrlFilter());
    filterRegistrationBean.setDispatcherTypes(DispatcherType.ASYNC,
        DispatcherType.ERROR, DispatcherType.FORWARD, DispatcherType.INCLUDE,
        DispatcherType.REQUEST);
    filterRegistrationBean.setOrder(filterOrder);
    return filterRegistrationBean;
  }
}
