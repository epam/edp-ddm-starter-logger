package com.epam.digital.data.platform.starter.logger.feign;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpMessage;

/**
 * Base class that implements common methods for both logbooks HttpRequest and HttpResponse
 */
public abstract class BaseFeignLogbookHttpEntity implements HttpMessage {

  private final MultiValueMap<String, String> headers;

  protected BaseFeignLogbookHttpEntity(Map<String, Collection<String>> headers) {
    this.headers = new LinkedMultiValueMap<>();
    headers.forEach(
        (s, strings) -> BaseFeignLogbookHttpEntity.this.headers.addAll(s, List.copyOf(strings)));
  }

  @Override
  public String getProtocolVersion() {
    return "HTTP/1.1";
  }

  @Override
  public HttpHeaders getHeaders() {
    return HttpHeaders.of(headers);
  }

  @Nullable
  @Override
  public String getContentType() {
    return headers.getFirst("Content-Type");
  }

  @Override
  public Charset getCharset() {
    var contentType = this.getContentType();
    if (Objects.isNull(contentType)) {
      return StandardCharsets.UTF_8;
    }
    return Objects.requireNonNullElse(MediaType.parseMediaType(contentType).getCharset(),
        StandardCharsets.UTF_8);
  }
}
