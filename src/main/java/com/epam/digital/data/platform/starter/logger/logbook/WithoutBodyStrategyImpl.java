package com.epam.digital.data.platform.starter.logger.logbook;

import org.springframework.lang.NonNull;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Strategy;

/**
 * Logbook {@link Strategy} that logs request and response without bodies.
 */
public class WithoutBodyStrategyImpl extends AbstractStrategy {

  @Override
  public HttpRequest process(@NonNull HttpRequest request) {
    return request.withoutBody();
  }

  @Override
  public HttpResponse process(@NonNull HttpRequest request, @NonNull HttpResponse response) {
    return response.withoutBody();
  }
}
