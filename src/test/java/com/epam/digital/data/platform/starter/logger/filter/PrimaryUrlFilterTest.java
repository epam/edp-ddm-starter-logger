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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PrimaryUrlFilterTest {

  private static final String URL = "url";

  @Mock
  private HttpServletRequest httpServletRequest;
  @Mock
  private HttpServletResponse servletResponse;
  @Mock
  private FilterChain filterChain;
  @Captor
  private ArgumentCaptor<HttpServletRequest> requestArgumentCaptor;

  @InjectMocks
  private PrimaryUrlFilter primaryUrlFilter;

  @Test
  public void testHttpRequestHasHeader() throws ServletException, IOException {
    when(httpServletRequest.getHeader(PrimaryUrlFilter.PRIMARY_URL_REQUEST_HEADER))
        .thenReturn(URL);

    primaryUrlFilter.doFilterInternal(httpServletRequest, servletResponse, filterChain);

    verify(filterChain).doFilter(httpServletRequest, servletResponse);
  }

  @Test
  public void testHttpRequestHasNotHeader() throws ServletException, IOException {
    when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(URL));

    primaryUrlFilter.doFilterInternal(httpServletRequest, servletResponse, filterChain);

    verify(filterChain).doFilter(requestArgumentCaptor.capture(), eq(servletResponse));
    var resultRequest = requestArgumentCaptor.getValue();
    assertThat(resultRequest.getHeader(PrimaryUrlFilter.PRIMARY_URL_REQUEST_HEADER)).isEqualTo(URL);
  }
}
