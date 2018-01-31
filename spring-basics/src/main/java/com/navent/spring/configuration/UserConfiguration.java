package com.navent.spring.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration("userConfiguration")
public class UserConfiguration {
	
	@Value("sclinis")
	private String user; 
	
	@Value("${user.configuration.server}")
	private String server;
	
	@Value("9000")
	private int port;
	
	@Value("${user.configuration.enabled}")
	private boolean enabled;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getFullAddress(String prefix) {
		return prefix + "." + user + "@" + server + ":" + port; 
	}
}
