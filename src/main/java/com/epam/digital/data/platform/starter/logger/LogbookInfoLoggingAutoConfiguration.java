package com.epam.digital.data.platform.starter.logger;

import com.epam.digital.data.platform.starter.logger.feign.LogbookFeignClient;
import com.epam.digital.data.platform.starter.logger.logbook.InfoHttpLogWriter;
import com.epam.digital.data.platform.starter.logger.logbook.RequestBodyOnDebugStrategy;
import com.epam.digital.data.platform.starter.logger.logbook.WithoutBodyStrategyImpl;
import feign.Client;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.DefaultHttpLogFormatter;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Strategy;

@Configuration
public class LogbookInfoLoggingAutoConfiguration {

  @Bean
  @ConditionalOnProperty(prefix = "logbook.info-logging", name = "enabled")
  public HttpLogWriter httpLogWriter() {
    return new InfoHttpLogWriter();
  }

  @Bean
  @ConditionalOnProperty(prefix = "logbook", name = "strategy", havingValue = "request-body-on-debug")
  public Strategy strategy() {
    return new RequestBodyOnDebugStrategy();
  }

  @Bean
  @ConditionalOnProperty(prefix = "logbook", name = "strategy", havingValue = "without-body")
  public Strategy withoutBodyStrategy() {
    return new WithoutBodyStrategyImpl();
  }

  @Bean
  @ConditionalOnProperty(prefix = "logbook", name = "http-log-writer", havingValue = "default", matchIfMissing = true)
  public HttpLogFormatter httpLogFormatter() {
    return new DefaultHttpLogFormatter();
  }

  @Bean
  @ConditionalOnProperty(prefix = "logbook.feign", name = "enabled")
  public Client client(Logbook logbook) {
    return new LogbookFeignClient(logbook);
  }
}
