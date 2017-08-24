package edu.upm.midas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class EidwApplication {

	public static void main(String[] args) {
		SpringApplication.run(EidwApplication.class, args);
	}
}
