package com.phucshop.demo.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phucshop.demo.dto.ProductDTO;
import com.phucshop.demo.entity.Category;
import com.phucshop.demo.entity.ProductVariant;
import com.phucshop.demo.service.ProductService;
import com.phucshop.demo.service.ProductVariantService;

@Controller
@RequestMapping("/product-detail/{slug}")
public class ProductDetailController {
	private static final Logger logger = LoggerFactory.getLogger(ProductDetailController.class);
		
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductVariantService productVariantService;
	
	@ModelAttribute("product")
	public ProductDTO get(@PathVariable String slug) {
		return productService.getProductBySlug(slug);
	}
	
	@GetMapping()
	public String show(@PathVariable String slug, Model model) {
		
		ProductDTO productDTO = (ProductDTO)model.getAttribute("product");
		
		// find products by category name
		List<ProductDTO> l = productService.getAllProductsByCategoryName(productDTO.getCategory());
		logger.info("day la san pham lien quan: " + l.toString());
		model.addAttribute("relatedProducts", l);
		
		return "product-detail";
	}
	
	
	
	@PostMapping("/get-size")
	@ResponseBody
	public Map<String, Integer> getColorFromSize(@RequestParam("color") String color, @RequestParam("id") String id, Model model){
		
		Integer id_product = productService.decodeProductId(id);
		
		
		Map<String, Integer> map =  productVariantService.getSizeAndQuantityFromColorName(color, id_product);
		return map;
	}
	
	

}
