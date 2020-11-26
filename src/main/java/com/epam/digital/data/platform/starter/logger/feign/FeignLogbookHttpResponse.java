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

import feign.Response;
import java.io.IOException;
import java.util.Objects;
import lombok.Getter;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Origin;

/**
 * Logbook representation of Feign response. Used for Logbook HTTP logging
 */
public class FeignLogbookHttpResponse extends BaseFeignLogbookHttpEntity implements HttpResponse {

  @Getter
  private Response response;
  private boolean withBody = false;
  private byte[] responseBody = new byte[0];

  private FeignLogbookHttpResponse(Response response) {
    super(response.headers());
    this.response = response;
  }

  /**
   * Static factory method that creates an instance of logbook {@link HttpResponse}
   *
   * @param response - Feign response to be logged
   * @return logbook {@link HttpResponse}
   */
  public static FeignLogbookHttpResponse from(Response response) {
    return new FeignLogbookHttpResponse(response);
  }

  @Override
  public int getStatus() {
    return response.status();
  }

  @Override
  public HttpResponse withBody() throws IOException {
    this.withBody = true;
    if (Objects.isNull(this.responseBody) && Objects.nonNull(this.response.body())) {
      this.responseBody = this.response.body().asInputStream().readAllBytes();
      this.response = this.response.toBuilder().body(responseBody).build();
    }
    return this;
  }

  @Override
  public HttpResponse withoutBody() {
    this.withBody = false;
    return this;
  }

  @Override
  public Origin getOrigin() {
    return Origin.REMOTE;
  }

  @Override
  public byte[] getBody() {
    return withBody ? responseBody : new byte[0];
  }
}
