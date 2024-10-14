package com.phucshop.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phucshop.demo.dto.ProductDTO;
import com.phucshop.demo.dto.ProductVariantDTO;
import com.phucshop.demo.entity.Category;
import com.phucshop.demo.entity.Product;
import com.phucshop.demo.repository.ProductRepository;
import com.phucshop.demo.service.CategoryService;
import com.phucshop.demo.service.ProductService;
import com.phucshop.demo.service.ProductVariantService;
import com.phucshop.demo.service.Service;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private final Service service;
	private final ProductService productService;
	private final CategoryService categoryService;
	private final ProductVariantService productVariantService;
	private final ProductRepository productRepository;
	
	
	private static final Logger log = LoggerFactory.getLogger(AdminController.class);



    AdminController(ProductService productService, CategoryService categoryService, Service service, 
    		ProductVariantService productVariantService, ProductRepository productRepository) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.service = service;
		this.productVariantService = productVariantService;
		this.productRepository = productRepository;
    }
	
	@GetMapping("")
	public String show() {
		return "admin-page";
	}
	
	@ModelAttribute("categories")
	public List<Category> categories(){
		return categoryService.findAll();
	}
	
	@ModelAttribute("Service")
	public Service service() {
		return this.service;
	}
	
	@GetMapping("/products")
	public String products(Model model) {
		model.addAttribute("products",productService.getAllProduct() );
		
		
		return "admin/admin-product";
	}
	
	@PostMapping("/products") 
    public String saveProduct1(@ModelAttribute Product product, Model model) {
            Product savedProduct = productRepository.save(product);
         // Add necessary attributes to the model
            model.addAttribute("products",productService.getAllProduct() );
            return "/admin/admin-product";

    }
	@PostMapping("/products/delete") 
    public String delete(@ModelAttribute Product product, Model model) {
            Product savedProduct = productRepository.save(product);
         // Add necessary attributes to the model
            model.addAttribute("products",productService.getAllProduct() );
            return "/admin/admin-product";

    }
	
	
	@GetMapping("/product-variant/{slug}")
	public String productVariant(Model model, @PathVariable String slug) {
		model.addAttribute("product", productService.getProductBySlug(slug));
		model.addAttribute("products",productVariantService.getbySlug(slug));
		
		return "admin/product-variant";
	}
	
	@PostMapping("/product-variant/{slug}") 
    public String saveProduct(@ModelAttribute Product product, @PathVariable String slug, Model model) {
			log.info(product.toString());
            Product savedProduct = productRepository.save(product);
            // Add necessary attributes to the model
            model.addAttribute("product", productService.getProductBySlug(slug));
            model.addAttribute("products", productVariantService.getbySlug(slug));
            return "/admin/product-variant";

    }
	
}
