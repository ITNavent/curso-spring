package com.navent.spring.external.pokemon;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Api Client for https://www.pokeapi.co/docsv2/
 * @author socrates-clinis
 *
 */
@Service
public class PokemonApiClient {
	
	@Value("${configuration.api.pokemon.url}")
	private String url;

	private static final Logger log = LoggerFactory.getLogger(PokemonApiClient.class);	
	
	@Autowired // http://www.baeldung.com/rest-template
	private RestTemplate restTemplate;
	
	
	public List<Pokemon> listPokemons(Integer from, Integer size) {
		try {
			return restTemplate.getForObject(url, PokemonResult.class, size, from).getResults();
		} catch (RestClientException e) {
			log.error("Error getting pokemon info", e);
			return null;
		}
	}
}
