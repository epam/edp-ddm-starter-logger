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
