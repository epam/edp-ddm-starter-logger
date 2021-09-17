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
