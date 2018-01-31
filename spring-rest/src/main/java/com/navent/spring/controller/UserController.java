package com.navent.spring.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.navent.spring.dto.UserDTO;
import com.navent.spring.model.User;
import com.navent.spring.service.PokemonServiceImpl;
import com.navent.spring.service.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController // crea un rest controller para atender los servicios rest https://spring.io/guides/tutorials/bookmarks/
@RequestMapping("/spring-example") // mapea parte de la URI
@Validated 	// Validating form inputs https://spring.io/guides/gs/validating-form-input/
@Api(value = "User Controller.", tags="user") // https://springframework.guru/spring-boot-restful-api-documentation-with-swagger-2/
public class UserController {

	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private PokemonServiceImpl pokemonService;
	
	@Autowired
    private ModelMapper modelMapper;
	

	@RequestMapping(value = "/users/{user_id}", method = RequestMethod.GET) // Mapea un request con argumentos usando @PathVariable
	@ApiOperation(value = "User", nickname = "GetUser", notes = "Obtiene un usuario por id.")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Retorna el usuario requerido"),
	        @ApiResponse(code = 204, message = "Usuario no encontrado", response = void.class),
	        @ApiResponse(code = 400, message = "Parámetros inválidos"),
	        @ApiResponse(code = 500, message = "Server Internal Error")
	})
	public UserDTO getUser(@PathVariable Long user_id) {
		return modelMapper.map(userService.getUser(user_id), UserDTO.class);
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET) // Mapea una request usando queryStrings con @RequestParam http://www.baeldung.com/spring-requestmapping
	public List<UserDTO> listUsers(@RequestParam(value = "user_name", required = false) String userName,
			@RequestParam(value = "date_from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		return userService.listUsers(userName, date)
							.stream()
							.map(u -> modelMapper.map(u, UserDTO.class))
							.collect(Collectors.toList());
	}

	@RequestMapping(value = "/users/create", method = RequestMethod.POST) // Post, para validar los argumentos uso @Valid. @see https://spring.io/guides/gs/validating-form-input/
	public void createUser(@Valid @RequestBody UserDTO userDto) {
		userService.createUser(modelMapper.map(userDto, User.class));
	}
	
	
	@RequestMapping(value = "/pokemons", method = RequestMethod.GET)
	public List<String> listPokemons() {
		return pokemonService.getPokemonNames();
	}
	
}
