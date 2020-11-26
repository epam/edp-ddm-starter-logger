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
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Sink;
import org.zalando.logbook.Strategy;

/**
 * Logbook {@link Strategy} that contains common logic for adding response code to MDC.
 */
public abstract class AbstractStrategy implements Strategy {

  private static final String RESPONSE_CODE_MDC_KEY = "responseCode";

  @Override
  public void write(
      @NonNull Correlation correlation,
      @NonNull HttpRequest request,
      @NonNull HttpResponse response,
      @NonNull Sink sink)
      throws IOException {
    try {
      MDC.put(RESPONSE_CODE_MDC_KEY, String.valueOf(response.getStatus()));
      sink.write(correlation, request, response);
    } finally {
      MDC.remove(RESPONSE_CODE_MDC_KEY);
    }
  }
}
