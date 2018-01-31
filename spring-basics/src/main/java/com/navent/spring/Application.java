package com.navent.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication // Configura una aplicación como spring boot https://spring.io/guides/gs/spring-boot/
@ComponentScan("com.navent") // Indica los paquetes donde escannear todos los componentes de spring
@EnableScheduling // habilito el scheduling https://spring.io/guides/gs/scheduling-tasks/
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	
	
}
