package com.navent.spring.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import com.navent.spring.validator.UserNameValidator;

import io.swagger.annotations.ApiModelProperty;

public class UserDTO {
	
	@ApiModelProperty(notes = "Id del usuario") // Swagger doc
	private Long userId;
	
	// Validating form inputs https://spring.io/guides/gs/validating-form-input/
	@NotNull
	@UserNameValidator
	@ApiModelProperty(notes = "Nombre del usuario") // Swagger doc
	private String userName;
	
	@Email
	@ApiModelProperty(notes = "E-mail del usuario") // Swagger doc
	private String mail;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
}
