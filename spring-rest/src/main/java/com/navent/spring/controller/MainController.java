package com.navent.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController // crea un rest controller para atender los servicios rest
@RequestMapping("/main")
public class MainController {

	//@see http://www.baeldung.com/spring-requestmapping
	@RequestMapping(value = {"/hello", "/hi"}, method = RequestMethod.GET) // atiende un servicio rest GET en /hello, y en hi
	public String helloWorld() {
		return "Hello World!!";
	}
	
	@RequestMapping(value = "*", method = RequestMethod.GET) // atiende el resto de las requests https://springframework.guru/spring-requestmapping-annotation/
	public String allFallback() {
		return "Fallback for All Requests";
	}
	
}
