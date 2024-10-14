package com.phucshop.demo.dto;

import lombok.Data;

@Data
public class CartItemDTO {
	
	private String productId;

	private String name;

	private int price;
	
	private String Slug;
	
	private String image;

	private String color;

	private String size;

	private int quantity;
}
