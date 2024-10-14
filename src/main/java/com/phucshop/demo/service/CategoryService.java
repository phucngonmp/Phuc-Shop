package com.phucshop.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phucshop.demo.entity.Category;
import com.phucshop.demo.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> findAll(){
		return categoryRepository.findAll();
	}
	
	public Category findById(int id) {
	    Optional<Category> optionalCategory = categoryRepository.findById(id);
	    if(optionalCategory.isPresent()) {
	        return optionalCategory.get();
	    } else {
	        // Handle the case where no Category was found with the provided id
	        return null; // or throw an exception, depending on your use case
	    }
	}

}
