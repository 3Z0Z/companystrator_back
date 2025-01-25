package com.companystrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CompanystratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanystratorApplication.class, args);
	}

}
