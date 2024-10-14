package com.phucshop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phucshop.demo.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
