package edu.upm.midas;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrix
@EnableDiscoveryClient
@EnableScheduling
public class EidwApplication implements CommandLineRunner {

	@Autowired
	private DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(EidwApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//cerateEmployee();
		//getEmployeeById();
		//employeeDAO.updateEmployeeEmailById(3, "kishan.cs2111@gamil.com");
		//employeeDAO.deleteEmployeeById(3);

		System.out.println("DataSource==="+dataSource);
	}
}
