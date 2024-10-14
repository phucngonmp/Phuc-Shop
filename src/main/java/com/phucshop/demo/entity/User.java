package com.phucshop.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity(name = "user")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
	private int id;

	

	@NotNull
	@Email(message = "Email should be valid")
	private String email;
	
	@NotNull
	@Size(min = 8, message = "Password must be at least 8 characters long")
	private String password;
	
	
	private byte role;

}
