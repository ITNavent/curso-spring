package com.navent.spring.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "configuration.example") // Crea el bean tomando las configuraciones que tengan el prefijo indicado
public class Config {
	private Boolean enabled;
	private String user;
	private List<String> roles;
	
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public String getFullUser(String prefix) {
		return prefix + ":" + user;
	}
	
	
}
