package com.navent.spring.service.rules;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.navent.spring.model.Posting;

@Component // indica que es un componente, con esto Spring la construye como un Bean
public class DescriptionRule extends GenericRule {

	@Value("#{'${configuration.rules.description.words}'.split(',')}") // Setea los valores del properties, en este caso es una lista de valores separadas por coma
	private List<String> words;
	
	@Override
	public boolean applies(Posting posting) {
		for (String word : words) {
			if(posting.getDescription().contains(word)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getMessage(Posting posting) {
		return getMessage(posting.getSite(), "message.posting.fraud.description");
	}
	
}
