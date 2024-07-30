package com.nagarro.si.cm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CandidateManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CandidateManagementApplication.class, args);
	}

}
