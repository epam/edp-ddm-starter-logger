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

package com.epam.digital.data.platform.starter.logger.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Request filter that checks if there is present X-Primary-Request-URL header and adds it if it
 * absent
 */
public class PrimaryUrlFilter extends OncePerRequestFilter {

  public static final String PRIMARY_URL_REQUEST_HEADER = "X-Primary-Request-URL";

  @Override
  public void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain chain) throws IOException, ServletException {
    if (Objects.nonNull(request.getHeader(PRIMARY_URL_REQUEST_HEADER))) {
      chain.doFilter(request, response);
      return;
    }

    var wrapper = new MutableHeadersHttpServletRequestWrapper(request);

    wrapper.addHeader(PRIMARY_URL_REQUEST_HEADER, request.getRequestURL().toString());

    chain.doFilter(wrapper, response);
  }

  /**
   * Wrapper class that is used for adding custom string headers to current servlet request.
   * <p>
   * {@link HttpServletRequestWrapper#getIntHeader(String)} and {@link
   * HttpServletRequestWrapper#getDateHeader(String)}} won't see custom headers until overriding.
   */
  private static class MutableHeadersHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final HttpHeaders customHttpHeaders = new HttpHeaders();

    public MutableHeadersHttpServletRequestWrapper(HttpServletRequest request) {
      super(request);
    }

    public void addHeader(String name, String value) {
      customHttpHeaders.add(name, value);
    }

    @Override
    public String getHeader(String name) {
      var headers = this.getHeaders(name);

      return headers.hasMoreElements() ? headers.nextElement() : null;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
      var keySet = new HashSet<>(customHttpHeaders.keySet());
      keySet.addAll(Collections.list(super.getHeaderNames()));

      return Collections.enumeration(keySet);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
      var superHeaders = super.getHeaders(name);
      if (!customHttpHeaders.containsKey(name)) {
        return superHeaders;
      }
      var list = Objects.nonNull(superHeaders) ?
          Collections.list(superHeaders) : new ArrayList<String>();
      list.addAll(Objects.requireNonNull(customHttpHeaders.get(name)));
      return Collections.enumeration(list);
    }
  }
}
