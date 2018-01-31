package com.navent.spring.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigSpEL {
	
	@Value("#{userConfiguration}") // Spring EL reference @see http://www.baeldung.com/spring-expression-language
	private UserConfiguration config;
	
	@Value("#{userConfiguration.enabled ? userConfiguration.user : 'default'}")
	private String user;
	
	@Value("#{userConfiguration.getFullAddress('navent')}")
	private String fullUser;
	

	public UserConfiguration getConfig() {
		return config;
	}

	public String getUser() {
		return user;
	}

	public String getFullUser() {
		return fullUser;
	}
	
}
