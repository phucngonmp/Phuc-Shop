package com.phucshop.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phucshop.demo.dto.ProductDTO;
import com.phucshop.demo.entity.Category;
import com.phucshop.demo.service.CategoryService;
import com.phucshop.demo.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private CategoryService categoryService;
	
	@ModelAttribute("categories")
	public List<Category> populateCategories() {
        return categoryService.findAll();
    }
	
	
	@GetMapping()
	public String showShop(Model model) {
		List<ProductDTO> l = productService.getAllProduct();
		logger.info(l.toString());
		model.addAttribute("products",l );
		
		return "product";
	}
	
	@GetMapping("/sort-by-price-low-to-high")
	public String sortByPriceASC(Model model) {
		model.addAttribute("sortBy", "price-low-to-high");
		model.addAttribute("products", productService.getAllProductsSortedByPriceASC());
		return "product";
	}
	
	@GetMapping("/sort-by-price-high-to-low")
	public String sortbyPriceDESC(Model model) {
		model.addAttribute("sortBy", "price-high-to-low");
		model.addAttribute("products", productService.getAllProductsSortedByPriceDESC());
		return "product";
	}

}
