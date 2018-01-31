package com.navent.spring.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.navent.spring.model.Fraud;
import com.navent.spring.model.Operation;
import com.navent.spring.model.Posting;
import com.navent.spring.model.Publisher;
import com.navent.spring.service.FraudService;

@Component
public class FraudScheduler {
	
	private static final Logger log = LoggerFactory.getLogger(FraudScheduler.class);
	
	@Value("#{'${configuration.sites}'.split(',')}")
	private List<String> sites;
	
	private List<String> words;
	
	@Autowired
	private FraudService fraudService;
	
	@Scheduled(fixedRate = 1000)
	//@Scheduled(cron="*/5 * * * * MON-FRI")
	public void scannFraudPostings() {
		Posting posting = createRandomPosting();
		Fraud fraudInfo = fraudService.getFraudInfo(posting);
		log.info(String.format("Posting: %s from %s isFraud:%s messages:%s", posting.getPostingId(), posting.getSite(), fraudInfo.getIsFraud(), fraudInfo.getMessages()));
	}

	private Posting createRandomPosting() {
		Posting posting = new Posting();
		posting.setPostingId(ThreadLocalRandom.current().nextLong(1, 99999));
		posting.setSite(sites.get(ThreadLocalRandom.current().nextInt(0, sites.size() - 1)));
		posting.setDescription(getRandomDescription(posting.getPostingId()));
		Operation operation = new Operation();
		operation.setCurrency("USD");
		operation.setOperation("sale");
		operation.setPrice(ThreadLocalRandom.current().nextLong(1, 1000));
		posting.setOperation(operation);
		Publisher publisher = new Publisher();
		publisher.setPublisherId(ThreadLocalRandom.current().nextLong(1, 99999));
		publisher.setPostingCount(ThreadLocalRandom.current().nextLong(1, 20));
		posting.setPublisher(publisher );
		return posting;
	}

	private String getRandomDescription(Long postingId) {
		String ret = "Test description for posting " + postingId;
		if(ThreadLocalRandom.current().nextBoolean()) {
			ret = ret + ". Special word " + words.get(ThreadLocalRandom.current().nextInt(0, words.size()-1));
		}
		return ret;
	}

	@PostConstruct
	public void initWords() {
		words = new ArrayList<>();
		words.add("paypal");
		words.add("transferencia");
		words.add("mercadopago");
		words.add("test");
		words.add("test1");
		words.add("test2");
		words.add("test3");
		words.add("test4");
	}
	
	
}
