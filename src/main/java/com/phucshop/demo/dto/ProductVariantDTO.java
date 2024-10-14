package com.phucshop.demo.dto;

import lombok.Data;

@Data
public class ProductVariantDTO {
	private Integer id;
	
	private ProductDTO product;
	
	private String color;
	
	private String size;
	
	private int quantity;
	
}
