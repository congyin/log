package com.zjrcu.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class LogdemoApplication {


	public static void main(String[] args) {
		SpringApplication.run(LogdemoApplication.class, args);
	}

}
