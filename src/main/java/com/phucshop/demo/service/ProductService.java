package com.phucshop.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.phucshop.demo.dto.CartItemDTO;
import com.phucshop.demo.dto.ProductDTO;
import com.phucshop.demo.entity.Product;
import com.phucshop.demo.repository.ProductRepository;
import com.phucshop.demo.repository.ProductVariantRepository;

@Service
public class ProductService extends CommonProductService{
	
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	public ProductService(ProductVariantRepository productVariantRepository) {
		super(productVariantRepository);
	}

	@Autowired
	private ProductRepository productRepository;
	
	
	public List<ProductDTO> getAllProduct(){
		return convertListProductsToDTO(productRepository.findAll(Sort.by(Sort.Direction.ASC, "price")));
	}
	
	public List<ProductDTO> getAllProductsSortedByPriceASC() {
        return convertListProductsToDTO(productRepository.findAll(Sort.by(Sort.Direction.ASC, "price")));
    }
	
	public List<ProductDTO> getAllProductsSortedByPriceDESC() {
        return convertListProductsToDTO(productRepository.findAll(Sort.by(Sort.Direction.DESC, "price")));
    }
	public List<ProductDTO> getAllProductsByCategoryName(String categoryName){
		return convertListProductsToDTO(productRepository.findByCategoryName(categoryName));
	}
	
	public ProductDTO getProductBySlug(String productSlug) {
        Optional<Product> product = productRepository.findBySlug(productSlug);
        ProductDTO productDTO = convertProductOptionalToDTO(product);

        return productDTO;
    }
	public CartItemDTO getCartItemBySlug(String productSlug, String color, String size) {
		Optional<Product> product = productRepository.findBySlug(productSlug);
        CartItemDTO item = convertProductOptionalToCartItemDTO(product, size, color);

        return item;
	}
	
	
	
	
	
	
	
}
