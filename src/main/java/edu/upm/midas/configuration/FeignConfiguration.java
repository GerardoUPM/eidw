package edu.upm.midas.configuration;

import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

  /*@Bean
  public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
    return new BasicAuthRequestInterceptor("user", "password");
  }*/

  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }

  public static final int SECONDS = 172800000;

  @Bean
  public Request.Options options() {

    return new Request.Options(SECONDS, SECONDS);
  }

}
