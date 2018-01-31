package com.navent.spring.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.navent.spring.dao.SequenceDao;
import com.navent.spring.dao.UserRepository;
import com.navent.spring.exceptions.UserExistsException;
import com.navent.spring.exceptions.UserNotFoundException;
import com.navent.spring.model.User;

@Service
public class UserServiceImpl {

	private static final String USERS = "users";

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SequenceDao sequenceDao;
	
	public User getUser(Long userId) {
		User user = userRepository.getById(userId);
		if(user == null) {
			throw new UserNotFoundException(String.format("User %s not exist.", userId));
		}
		return user; 
	}
	
	@Cacheable(value="userlist", unless="#result == null") // caching https://spring.io/guides/gs/caching/
	public List<User> listUsers(String userName, LocalDate date) {
		if(userName != null) {
			return userRepository.findByUserNameIgnoreCaseContaining(userName);
		}
		if(date != null) {
			return userRepository.findByCreationDateGreaterThan(date);
		}
		
		return userRepository.findAll();
	}

	@CacheEvict(value = "userlist", allEntries=true)
	public void createUser(User user) {
		if(userRepository.existsByUserNameIgnoreCase(user.getUserName())) {
			throw new UserExistsException(String.format("The user %s already exists", user.getUserName()));
		}
		user.setId(sequenceDao.getNextSequenceId(USERS));
		user.setCreationDate(LocalDate.now());
		userRepository.save(user);
	}
	
}
