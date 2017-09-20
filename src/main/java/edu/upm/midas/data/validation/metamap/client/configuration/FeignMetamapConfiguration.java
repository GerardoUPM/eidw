package edu.upm.midas.data.validation.metamap.client.configuration;

import feign.Logger;
import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "feign.compression.request.enabled", matchIfMissing = false)
public class FeignMetamapConfiguration {

  @Bean
  public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
    return new BasicAuthRequestInterceptor("user", "password");
  }

  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }

  public static final int TEN_SECONDS = 172800000;

  @Bean
  public Request.Options options() {
    return new Request.Options(TEN_SECONDS, TEN_SECONDS);
  }

}
