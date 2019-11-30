package com.belong.phone.number.service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Phone book service",
        description = "a service to manage connectivity of phone number to clients",
        version = "1.0.0",
        contact = @Contact(
                name = "Henry-John Davis",
                email = "hjdrsa@gmail.com"
        ),
        license = @License(
                name = "APACHE LICENSE, VERSION 2.0",
                url = "https://www.apache.org/licenses/LICENSE-2.0"
        )
))
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
