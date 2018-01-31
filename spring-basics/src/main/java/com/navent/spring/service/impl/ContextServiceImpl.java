package com.navent.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.navent.spring.service.rules.PriceRule;

@Service
public class ContextServiceImpl {

	@Autowired
	private ApplicationContext applicationContext;

	public String example() {
		PriceRule priceRule = applicationContext.getBean(PriceRule.class);
		String a = applicationContext.getEnvironment().resolvePlaceholders("key");
		String b = applicationContext.getEnvironment().getProperty("key");
		
		return null;
		
	}
}
