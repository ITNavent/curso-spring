package com.navent.spring.service;

import com.navent.spring.model.Fraud;
import com.navent.spring.model.Posting;

public interface FraudService {
	
	public Fraud getFraudInfo(Posting posting);
}
