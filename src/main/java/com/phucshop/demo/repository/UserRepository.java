package com.phucshop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phucshop.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByEmail(String email);
}
