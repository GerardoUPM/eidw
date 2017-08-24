package edu.upm.midas.data.validation.tvp.client.configuration;

import feign.Logger;
import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignTvpConfiguration {

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
