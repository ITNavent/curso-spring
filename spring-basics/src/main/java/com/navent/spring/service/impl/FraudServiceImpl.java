package com.navent.spring.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.navent.spring.configuration.Config;
import com.navent.spring.configuration.ConfigSpEL;
import com.navent.spring.model.Fraud;
import com.navent.spring.model.Posting;
import com.navent.spring.service.FraudService;
import com.navent.spring.service.rules.GenericRule;

@Service // Es una especializacion de @Component, Indica que es un bean con lógica de negocio
public class FraudServiceImpl implements FraudService {
	
	private static final Logger log = LoggerFactory.getLogger(FraudServiceImpl.class);		
	
	@Autowired // sirve para inyectar un Bean
	private Config config;
	
	@Autowired
	private ConfigSpEL configSpEL;
	
	@Autowired // En este caso Inyecta una lista de beans (todas las subclases o implementan una interfaz)
	private List<GenericRule> rules;
	
	@Override
	public Fraud getFraudInfo(Posting posting) {
		Fraud fraud = new Fraud();
		fraud.setPostingId(posting.getPostingId());
		if(config.getEnabled()) {
			log.info(String.format("Evaluating fraud on posting %s", posting.getPostingId()));
			for (GenericRule rule : rules) {
				if(rule.applies(posting)) {
					log.info(String.format("Applies Rule %s", rule.getClass().getName()));
					fraud.setIsFraud(true);
					fraud.getMessages().add(rule.getMessage(posting));
				}
			}
			log.info(configSpEL.getFullUser());
		}
		return fraud;
	}
	
	
}
