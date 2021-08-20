package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EnableCaching
@SpringBootApplication(scanBasePackages={
        
        "com.config",
        "com.auth",
        "com.controllers",
        "com.entity",
        "com.repository",
        "com.webconfig",
        "com.service",
        "com.userdetail"
})
@EntityScan("com.entity")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
