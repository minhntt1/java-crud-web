package com.example.classicmodelsweb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.classicmodelsweb.dao.LoginAccDAO;

@SpringBootApplication
public class ClassicmodelsWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClassicmodelsWebApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(LoginAccDAO accDAO) {
		return runner -> {
//			List<String> list=accDAO.getAllCustomers();
//			list.stream().forEach(x->{
//				System.out.println(x);
//			});
		};
	}
}
