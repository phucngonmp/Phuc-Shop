package com.phucshop.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phucshop.demo.entity.Size;

public interface SizeRepository extends JpaRepository<Size, Integer>{
List<Size> findByName(String name);
}
