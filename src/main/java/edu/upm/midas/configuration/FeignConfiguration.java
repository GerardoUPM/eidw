package edu.upm.midas.configuration;

import feign.Logger;
import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

  public static int connectTimeOutMillis = 259200000;
  public static int readTimeOutMillis = 259200000;

  /*@Bean
  public Contract feignContract() {
    return new feign.Contract.Default();
  }*/

  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }
  @Bean
  public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
    return new BasicAuthRequestInterceptor("admin", "admin");
  }

  /*

  public static final int SECONDS = 259200000;

  */


  /**
   * Configuración elemental para consumir servicios Web
   *
   * @return
   */
  @Bean
  public Request.Options options() {

    return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
  }

}
