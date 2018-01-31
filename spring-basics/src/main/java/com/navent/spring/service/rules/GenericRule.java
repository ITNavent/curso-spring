package com.navent.spring.service.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.navent.spring.model.Posting;
import com.navent.spring.utils.SiteLocale;

public abstract class GenericRule {
	
	@Autowired
	private MessageSource messageSource; // el message source se inicializa como Bean en @configuration

	public abstract boolean applies(Posting posting);
	public abstract String getMessage(Posting posting);

	protected String getMessage(String site, String message, String ... params) {
		return messageSource.getMessage(message, params, SiteLocale.locale(site));
	}
	
	
}
