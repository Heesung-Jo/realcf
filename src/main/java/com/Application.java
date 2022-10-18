package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EnableCaching
@EnableScheduling
@SpringBootApplication(scanBasePackages={
        
        "com.config",
        "com.auth",
        "com.controllers",
        "com.entity",
        "com.entity_internal",
        "com.repository",
        "com.repository_internal",
        "com.webconfig",
        "com.service",
        "com.service_internal",
        "com.userdetail",
        "com.aspect"
})
@EntityScan({"com.entity", "com.entity_internal"})
public class Application {

		
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
