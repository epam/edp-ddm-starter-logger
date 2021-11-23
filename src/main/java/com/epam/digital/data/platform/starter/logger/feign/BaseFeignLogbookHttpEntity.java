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
