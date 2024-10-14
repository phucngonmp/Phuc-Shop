package com.phucshop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.phucshop.demo.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
