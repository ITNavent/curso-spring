package com.navent.spring.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Value;

public class UserNameValidatorConstraint implements ConstraintValidator<UserNameValidator, String>{
	
	@Value("#{'${configuration.validation.username.invalid}'.split(',')}") 
	private List<String> invalidUserName;
	
	@Override
	public void initialize(UserNameValidator constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return !invalidUserName.contains(value.toLowerCase());
	}

}
