package com.phucshop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phucshop.demo.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

}
