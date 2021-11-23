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

import feign.Client.Default;
import feign.Request;
import feign.Response;
import java.io.IOException;
import org.zalando.logbook.Logbook;

/**
 * Base Feign client that logs every request and response using Logbook.
 */
public class LogbookFeignClient extends Default {

  private final Logbook logbook;

  public LogbookFeignClient(Logbook logbook) {
    super(null, null);
    this.logbook = logbook;
  }

  @Override
  public Response execute(Request request, Request.Options options) throws IOException {
    var logbookRequest = FeignLogbookHttpRequest.from(request);
    var processing = logbook.process(logbookRequest).write();

    var response = super.execute(request, options);

    var logbookResponse = FeignLogbookHttpResponse.from(response);
    processing.process(logbookResponse).write();

    return logbookResponse.getResponse();
  }
}
