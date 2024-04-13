package com.robertponce.W5L2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.robertponce.W5L2.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	public Optional<User> findByEmail(String email);
	
	public List<User> findAll();

}
