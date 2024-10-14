package com.phucshop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phucshop.demo.entity.OrderInfo;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Integer>{
}
