package com.navent.spring.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.navent.spring.model.User;
import com.navent.spring.model.UserType;
// @see http://www.baeldung.com/spring-data-mongodb-tutorial
public interface UserRepository extends MongoRepository<User, Long> {
	public User getById(Long id);
	public User findByMail(String mail);
	public boolean existsByUserNameIgnoreCase(String userName);
	public long countByUserType(UserType usertype);
	public long deleteByMail(String mail);
	public List<User> findByUserNameIgnoreCaseContaining(String userName);
	public List<User> findByCreationDateGreaterThan(LocalDate userId);
	public List<User> findByIdBetween(Long from, Long to);
}
