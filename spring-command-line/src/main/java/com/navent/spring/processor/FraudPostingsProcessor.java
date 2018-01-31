package com.navent.spring.processor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.navent.spring.model.Posting;
import com.navent.spring.service.FraudService;

@Service("FraudPostingsProcessor")
public class FraudPostingsProcessor implements PostingsProcessor {

	private static final Logger log = LoggerFactory.getLogger(FraudPostingsProcessor.class);

	@Autowired
	private FraudService fraudService;
	
	private Gson gson;

	@Override
	public void process(String output, List<Posting> postings) {

		List<String> frauds = postings.parallelStream()
										.map(this::getFraudInfo)
										.collect(Collectors.toList());
		try {
			Files.write(Paths.get(output), frauds);
		} catch (IOException e) {
			log.error("Error writting procces output", e);
		}
	}

	private String getFraudInfo(Posting posting) {
		return gson.toJson(fraudService.getFraudInfo(posting));
	}
	
	@PostConstruct
	public void init() {
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		gson = builder.create();
	}

}
