package com.navent.spring.service.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.navent.spring.model.Posting;
import com.navent.spring.service.PublisherScoreService;

@Component  // indica que es un componente, con esto Spring la construye como un Bean
public class PublisherRule extends GenericRule {

	@Autowired
	private PublisherScoreService publisherScoreService;
	
	@Value("${configuration.rules.publisher.minscore:1}")
	private Long minScore;
	
	@Override
	public boolean applies(Posting posting) {
		Long score = publisherScoreService.getScore(posting.getPublisher());
		return score!=null && score.compareTo(minScore) <= 0;
	}

	@Override
	public String getMessage(Posting posting) {
		return getMessage(posting.getSite(), "message.posting.fraud.publisher");
	}

}
