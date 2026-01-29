package com.sopheak.istadfinalems;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IstadFinalEmployeeManagementSystemProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(IstadFinalEmployeeManagementSystemProjectApplication.class, args);
	}

}
