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
@Deprecated
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
