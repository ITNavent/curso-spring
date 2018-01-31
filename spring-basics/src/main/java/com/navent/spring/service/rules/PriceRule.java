package com.navent.spring.service.rules;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.navent.spring.model.Posting;

@Component  // indica que es un componente, con esto Spring la construye como un Bean
public class PriceRule extends GenericRule {

	@Value("${configuration.rules.price.min:100}") // toma el valor de la configuracion con un default en caso que no esté
	private Long minPrice;
	
	@Override
	public boolean applies(Posting posting) {
		return posting.getOperation().getPrice().compareTo(minPrice) < 0;
	}

	@Override
	public String getMessage(Posting posting) {
		return getMessage(posting.getSite(), "message.posting.fraud.price", String.valueOf(minPrice));
	}
	
}
