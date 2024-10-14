package com.phucshop.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity(name="order1")
public class Order{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="id_order_info")
	private OrderInfo oderInfo;
	
	private int price;
	
	private byte status;
	
	
	
	@ManyToOne
	@JoinColumn(name="id_user")
	private User user;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private LocalDate create_at;
	
	
	
	
}
