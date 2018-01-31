package com.navent.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.navent.spring.model.Publisher;
import com.navent.spring.service.PublisherScoreService;

@Service // Es una especializacion de @Component, Indica que es un bean con lógica de negocio
public class PublisherScoreServiceImpl implements PublisherScoreService {

	@Value("#{${configuration.publisher.score.ranges}}") // se inyecta con los valores definidos en el properties, en esta caso es un mapa
    private List<Map<String, Long>> rangeConfig;

	private List<PublisherScore> ranges;
	
	@Override
	public Long getScore(Publisher publisher) {
		for (PublisherScore range : ranges) {
			if(range.applies(publisher.getPostingCount())) {
				return range.getScore();
			}
		}
		return null;
	}
	
	@PostConstruct //Se ejecuta al construir el bean
	public void init() {
		this.ranges = new ArrayList<PublisherScore>();
		for (Map<String, Long> map : rangeConfig) {
			PublisherScore range = new PublisherScore();
			range.setFrom(map.get("from"));
			range.setTo(map.get("to"));
			range.setScore(map.get("value"));
			this.ranges.add(range);
		}
	}
	
	@PreDestroy // se ejecuta al finalizar el bean (se utiliza por ej para cerrar conexiones).
	public void destroy() {
		
	}

}
