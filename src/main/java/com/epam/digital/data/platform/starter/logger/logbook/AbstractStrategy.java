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
