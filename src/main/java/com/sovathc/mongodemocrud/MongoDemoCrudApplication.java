package com.sovathc.mongodemocrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MongoDemoCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoDemoCrudApplication.class, args);
	}

}
