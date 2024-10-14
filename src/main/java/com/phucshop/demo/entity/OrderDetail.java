package com.phucshop.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity(name = "order_detail")
@Data
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="id_productvariant")
	private ProductVariant productVariant;
	
	
	private Integer quantity;
	
	@ManyToOne
	@JoinColumn(name="id_order")
	private Order order;

}
