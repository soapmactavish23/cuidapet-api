package com.hkprogrammer.api.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hkprogrammer.api.domain.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Boolean existsUserByEmail(String email);
	
	Optional<User> findByEmail(String email);
	
}
