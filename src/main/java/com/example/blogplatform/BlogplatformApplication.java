package com.example.blogplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.blogplatform.property.CookieProperties;

@SpringBootApplication
@EnableConfigurationProperties(CookieProperties.class)
public class BlogplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogplatformApplication.class, args);
	}

}
