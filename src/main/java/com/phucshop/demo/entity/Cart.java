package com.phucshop.demo.entity;

import java.util.HashMap;
import java.util.Map;

import com.phucshop.demo.dto.CartItemDTO;
import com.phucshop.demo.dto.ProductDTO;

import lombok.Data;

@Data
public class Cart {
	private Map<CartItemDTO, Integer> items;
	
	public Cart() {
        this.items = new HashMap<>();
    }
	
	public void addToCart(CartItemDTO item) {
		items.put(item, items.getOrDefault(item,0)+1);
	}
	
	public void removeProduct(CartItemDTO item) {
		items.remove(item);
	}
	
	public void removeAll() {
		items.clear();
	}
	
	public int getTotalItems() {
		int sum = 0;
		
		for(CartItemDTO item : items.keySet()) {
			sum += items.get(item);
		}
		
		return sum;
	}
	
	public int getTotalPrice() {
		int total = 0;
		
		for(CartItemDTO item : items.keySet()) {
			total += item.getPrice()*items.get(item);
		}
		
		return total;
	}
}
