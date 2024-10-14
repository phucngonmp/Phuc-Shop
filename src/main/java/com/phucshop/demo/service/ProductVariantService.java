package com.phucshop.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phucshop.demo.dto.ProductDTO;
import com.phucshop.demo.dto.ProductVariantDTO;
import com.phucshop.demo.entity.Product;
import com.phucshop.demo.entity.ProductVariant;
import com.phucshop.demo.repository.ProductVariantRepository;

@Service
public class ProductVariantService extends CommonProductService{
	
	private ProductVariantRepository productVariantRepository;
	
	public ProductVariantService(ProductVariantRepository productVariantRepository) {
		super(productVariantRepository);
		this.productVariantRepository = productVariantRepository;
	}

	
	// find all variants of a product by slug 
	public List<ProductVariantDTO> getbySlug(String slug){
		List<ProductVariantDTO> list = new ArrayList<>();
		
		for(ProductVariant p : productVariantRepository.findBySlug(slug)) {
			list.add(convertProductVariantToDTO(p));
		}		
		return list;
	}
	
	public Map<String, Integer> getSizeAndQuantityFromColorName(String colorName, Integer id_product){
		Map<String, Integer> map = new HashMap<>();

		for(Object[] o : productVariantRepository.findSizeAndQuantityByColorName(colorName, id_product)) {
			String size = (String) o[0];
			Integer quantity = (Integer) o[1];
			map.put(size, quantity);
		}
		return map;
	}
	
	
	
	public boolean checkProductAvailable(Product product) {
		for(ProductVariant p : productVariantRepository.findByProduct(product)) {
			if(p.getQuantity() > 0)
				return true;
		}
		return false;
	}
	
	public boolean checkVariantAvailable(ProductVariant product) {
		return product.getQuantity() > 0;
	}
	
	
	private ProductVariantDTO convertProductVariantToDTO(ProductVariant productVariant) {
		
			ProductVariantDTO productVariantDTO = new ProductVariantDTO();
			
			productVariantDTO.setProduct(convertProductToProductDTO(productVariant.getProduct()));
			productVariantDTO.setColor(productVariant.getColor().getName());
			productVariantDTO.setQuantity(productVariant.getQuantity());
			productVariantDTO.setSize(productVariant.getSize().getName());
			
			return productVariantDTO;
			
	}
	
}
