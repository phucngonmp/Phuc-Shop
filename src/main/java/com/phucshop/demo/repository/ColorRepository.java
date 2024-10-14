package com.phucshop.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phucshop.demo.entity.Color;

public interface ColorRepository extends JpaRepository<Color, Integer>{
	List<Color> findByName(String name);

}
