package com.epam.digital.data.platform.starter.logger.feign;

import feign.Request;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Origin;

/**
 * Logbook representation of Feign request. Used for Logbook HTTP logging
 */
public class FeignLogbookHttpRequest extends BaseFeignLogbookHttpEntity implements HttpRequest {

  private final Request request;
  private final URI requestUri;
  private boolean withBody = false;

  private FeignLogbookHttpRequest(Request request) {
    super(request.headers());
    this.request = request;
    this.requestUri = URI.create(request.url());
  }

  /**
   * Static factory method that creates an instance of logbook {@link HttpRequest}
   *
   * @param request - Feign request to be logged
   * @return logbook {@link HttpRequest}
   */
  public static FeignLogbookHttpRequest from(Request request) {
    return new FeignLogbookHttpRequest(request);
  }

  @Override
  public String getRemote() {
    return "localhost";
  }

  @Override
  public String getMethod() {
    return request.httpMethod().name();
  }

  @Override
  public String getScheme() {
    return Objects.requireNonNullElse(requestUri.getScheme(), "");
  }

  @Override
  public String getHost() {
    return Objects.requireNonNullElse(requestUri.getHost(), "");
  }

  @Override
  public Optional<Integer> getPort() {
    return requestUri.getPort() == -1 ? Optional.empty() : Optional.of(requestUri.getPort());
  }

  @Override
  public String getPath() {
    return Objects.requireNonNullElse(requestUri.getPath(), "");
  }

  @Override
  public String getQuery() {
    return Objects.requireNonNullElse(requestUri.getQuery(), "");
  }

  @Override
  public HttpRequest withBody() {
    this.withBody = true;
    return this;
  }

  @Override
  public HttpRequest withoutBody() {
    this.withBody = false;
    return this;
  }

  @Override
  public Origin getOrigin() {
    return Origin.LOCAL;
  }

  @Override
  public byte[] getBody() {
    return withBody && Objects.nonNull(request.body()) ? request.body() : new byte[0];
  }
}
