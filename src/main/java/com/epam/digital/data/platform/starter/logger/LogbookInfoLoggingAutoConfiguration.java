/*
 * Copyright 2025 EPAM Systems.
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

package com.epam.digital.data.platform.starter.logger;

import com.epam.digital.data.platform.starter.logger.feign.LogbookFeignClient;
import com.epam.digital.data.platform.starter.logger.logbook.FilteringStrategyDecorator;
import com.epam.digital.data.platform.starter.logger.logbook.InfoHttpLogWriter;
import com.epam.digital.data.platform.starter.logger.logbook.RequestBodyOnDebugStrategy;
import com.epam.digital.data.platform.starter.logger.logbook.WithRequestBodyStrategy;
import com.epam.digital.data.platform.starter.logger.logbook.WithoutBodyStrategyImpl;
import com.epam.digital.data.platform.starter.logger.logbook.config.LogbookStrategyProperties;
import feign.Client;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.DefaultHttpLogFormatter;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Strategy;

@Configuration
@EnableConfigurationProperties(LogbookStrategyProperties.class)
public class LogbookInfoLoggingAutoConfiguration {

  @Bean
  @ConditionalOnProperty(prefix = "logbook.info-logging", name = "enabled")
  public HttpLogWriter httpLogWriter() {
    return new InfoHttpLogWriter();
  }

  @Bean
  @ConditionalOnProperty(prefix = "logbook", name = "strategy", havingValue = "request-body-on-debug")
  public Strategy requestBodyOnDebugStrategy(LogbookStrategyProperties properties) {
    return createFilteredStrategy(new RequestBodyOnDebugStrategy(),
        "request-body-on-debug",
        properties);
  }

  @Bean
  @ConditionalOnProperty(prefix = "logbook", name = "strategy", havingValue = "without-body")
  public Strategy withoutBodyStrategy(LogbookStrategyProperties properties) {
    return createFilteredStrategy(new WithoutBodyStrategyImpl(),
        "without-body",
        properties);
  }

  @Bean
  @ConditionalOnProperty(prefix = "logbook", name = "strategy", havingValue = "with-request-body")
  public Strategy withRequestBodyStrategy(LogbookStrategyProperties properties) {
    return createFilteredStrategy(new WithRequestBodyStrategy(),
        "with-request-body",
        properties);
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

  private Strategy createFilteredStrategy(Strategy baseStrategy,
      String strategyName,
      LogbookStrategyProperties properties) {
    List<LogbookStrategyProperties.PathConfig> pathConfigs;
    if (properties.getStrategyPredicate() == null) {
      pathConfigs = Collections.emptyList();
    } else {
      pathConfigs = properties.getStrategyPredicate().getOrDefault(strategyName, Collections.emptyList());
    }

    return new FilteringStrategyDecorator(baseStrategy, pathConfigs);
  }
}