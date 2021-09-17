package com.epam.digital.data.platform.starter.logger.logbook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;

/**
 * Logbook {@link HttpLogWriter} that logs requests and responses using Slf4j in info mode.
 */
@Slf4j
public class InfoHttpLogWriter implements HttpLogWriter {

  @Override
  public void write(@NonNull Precorrelation precorrelation, @NonNull String request) {
    log.info(request);
  }

  @Override
  public void write(@NonNull Correlation correlation, @NonNull String response) {
    log.info(response);
  }
}
