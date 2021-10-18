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
