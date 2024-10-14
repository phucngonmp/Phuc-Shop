package com.phucshop.demo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	private int price;
	
	@NotNull
	private String name;
	
	private String image;
	
	private int sold;
	
	private byte isshow;
	
	@ManyToOne
	@JoinColumn(name="id_category")
	private Category category;
	
	@OneToMany(mappedBy = "product")
    private List<ProductVariant> variants;
	
	@NotNull
	@Column(unique = true)
	private String slug;
	
	
}
