package com.phucshop.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phucshop.demo.entity.User;
import com.phucshop.demo.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        // Perform any necessary validation or business logic here
        // Then save the user to the database
        return userRepository.save(user);
    }
    
    public User findByEmail(String email) {
    	return userRepository.findByEmail(email);
    }
    
    public boolean checkAccount(String email, String pass) {
    	return userRepository.findByEmail(email).getPassword().equals(pass);
    }
}
