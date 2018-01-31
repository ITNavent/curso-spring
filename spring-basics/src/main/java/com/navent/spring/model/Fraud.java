package com.navent.spring.model;

import java.util.ArrayList;
import java.util.List;

public class Fraud {
	private Long postingId;
	private Boolean isFraud = Boolean.FALSE;
	private List<String> messages = new ArrayList<>();
	
	public Long getPostingId() {
		return postingId;
	}
	public void setPostingId(Long postingId) {
		this.postingId = postingId;
	}
	public Boolean getIsFraud() {
		return isFraud;
	}
	public void setIsFraud(Boolean isFraud) {
		this.isFraud = isFraud;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
}
