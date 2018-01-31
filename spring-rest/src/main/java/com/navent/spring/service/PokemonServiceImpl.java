package com.navent.spring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.navent.spring.exceptions.PokemonNotFoundException;
import com.navent.spring.external.pokemon.Pokemon;
import com.navent.spring.external.pokemon.PokemonApiClient;

@Service
public class PokemonServiceImpl {
	
	@Value("${configuration.api.pokemon.size:100}")
	private int pageSize;

	@Value("${configuration.api.pokemon.offset:0}")
	private int offset;
	
	@Autowired
	private PokemonApiClient pokemonApi;
	

	@Cacheable(value="pokemonlist")
	public List<String> getPokemonNames() {
		List<Pokemon> pokemons = pokemonApi.listPokemons(offset, pageSize);
		if(pokemons == null) {
			throw new PokemonNotFoundException("Not Found Pokemons");
		}
		return pokemons.stream()
						.map(Pokemon::getName)
						.collect(Collectors.toList());
	}
	
	
}
