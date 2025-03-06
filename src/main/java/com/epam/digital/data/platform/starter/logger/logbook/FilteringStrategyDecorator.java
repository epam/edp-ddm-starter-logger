/*
 * Copyright 2025 EPAM Systems.
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

import com.epam.digital.data.platform.starter.logger.logbook.config.LogbookStrategyProperties.PathConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.util.AntPathMatcher;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Strategy;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class FilteringStrategyDecorator implements Strategy {

  private final Strategy delegate;
  private final List<PathConfig> pathConfigs;
  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  @Override
  public HttpRequest process(HttpRequest request) throws IOException {
    if (shouldProcessRequest(request)) {
      return delegate.process(request);
    }
    return request;
  }

  @Override
  public HttpResponse process(HttpRequest request, HttpResponse response) throws IOException {
    if (shouldProcessRequest(request)) {
      return delegate.process(request, response);
    }
    return response;
  }

  private boolean shouldProcessRequest(HttpRequest request) {
    if (pathConfigs == null || pathConfigs.isEmpty()) {
      return false;
    }

    String path = request.getPath();
    String method = request.getMethod();

    return pathConfigs.stream()
        .anyMatch(config ->
            pathMatcher.match(config.getPath(), path) &&
                (config.getMethods() == null ||
                    config.getMethods().isEmpty() ||
                    config.getMethods().contains(method))
        );
  }
}