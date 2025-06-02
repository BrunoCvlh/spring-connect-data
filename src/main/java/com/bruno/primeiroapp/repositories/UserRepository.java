package com.bruno.primeiroapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bruno.primeiroapp.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
}
