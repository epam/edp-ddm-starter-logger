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
