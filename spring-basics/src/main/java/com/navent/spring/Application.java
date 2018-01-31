package com.navent.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication // Configura una aplicación como spring boot https://spring.io/guides/gs/spring-boot/
@ComponentScan("com.navent") // Indica los paquetes donde escannear todos los componentes de spring
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}
