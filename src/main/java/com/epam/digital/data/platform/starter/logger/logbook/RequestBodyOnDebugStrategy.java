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

package com.epam.digital.data.platform.starter.logger.logbook;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Strategy;

/**
 * Logbook {@link Strategy} that logs request and response bodies if Slf4j debug mode is enabled.
 */
@Slf4j
public class RequestBodyOnDebugStrategy extends AbstractStrategy {

  @Override
  public HttpRequest process(@NonNull HttpRequest request) throws IOException {
    return log.isDebugEnabled() ? request.withBody() : request.withoutBody();
  }

  @Override
  public HttpResponse process(
      @NonNull HttpRequest request,
      @NonNull HttpResponse response) throws IOException {
    return log.isDebugEnabled() ? response.withBody() : response.withoutBody();
  }
}
