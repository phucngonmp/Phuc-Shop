package com.phucshop.demo.dto;

import java.util.List;
import java.util.Map;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.Data;

@Data
public class ProductDTO {
	
	private String id;
	
	private String name;
	
	private int price;
	
	private String image;
	
	private String category;
	
	private int sold;
	
	private byte isshow;

	private String slug;
	
	private List<Integer> variantIds;

	private List<String> colors;
	
	//this is the sizes of the selected color 
	private Map<String, Integer > sizeAndQuantity;
	
}
