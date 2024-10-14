package com.phucshop.demo.controller;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.phucshop.demo.dto.CartItemDTO;
import com.phucshop.demo.dto.ProductDTO;
import com.phucshop.demo.entity.Cart;
import com.phucshop.demo.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {
	private static final Logger logger = LoggerFactory.getLogger(CartController.class);
	@Autowired
	private ProductService productService;
	
	@GetMapping()
	public String showCart() {
		return "shoping-cart";
	}
	// day la cach su dung @responsebody
	
	@PostMapping("/add-to-cart")
	@ResponseBody
	public Map<String, Object> addtoCart( @RequestParam String productSlug,HttpSession session, 
			@RequestParam String color, @RequestParam String size) {
		if(session.getAttribute("cart") == null) {
			session.setAttribute("cart", new Cart());
		}
		
		Map<String, Object> response = new HashMap<>();
		
		Cart cart = (Cart) session.getAttribute("cart");
		
		CartItemDTO item = productService.getCartItemBySlug(productSlug, color, size);
		if (item != null) {
	        
	        for(int i = 0; i <= 9; i++) {
	        	cart.addToCart(item);
	        }
	        logger.info("Added to cart successfully");
	        
	        int totalItems = cart.getTotalItems();
	        int totalPrice = cart.getTotalPrice();
	        
	        session.setAttribute("totalItems", totalItems);
	        session.setAttribute("totalPrice", totalPrice);
	        
	        
	        response.put("totalItems", totalItems);
	        response.put("totalPrice", totalPrice);

	        // Add cart items information
	        
	        response.put("cartItems", convertCartItemsToJsonArray(cart)); 
	        // o day neu chi dung cart.getProducts() no se tra ve fe
	        // 1 string gom key va value nen rat kho thao tac
	        
	        logger.info(response.toString());

	    } else {
	    	response.put("error", "product not found");
	    }
		 return response;
	}
	private ArrayNode convertCartItemsToJsonArray(Cart cart) {
	    ObjectMapper mapper = new ObjectMapper();
	    ArrayNode jsonArray = mapper.createArrayNode();

	    for (CartItemDTO item : cart.getItems().keySet()) {
	        ObjectNode itemJson = mapper.createObjectNode();
	        itemJson.put("name", item.getName());
	        itemJson.put("slug", item.getSlug());
	        itemJson.put("image", item.getImage());
	        itemJson.put("quantity", cart.getItems().get(item));
	        itemJson.put("price", item.getPrice());
	        itemJson.put("size", item.getSize());
	        itemJson.put("color", item.getColor());
	        itemJson.put("idProduct", item.getProductId());

	        jsonArray.add(itemJson);
	    }

	    return jsonArray;
	}
	
	

	/*	
	@PostMapping("/add-to-cart")
	public ResponseEntity<String> addtoCart( @RequestParam String productSlug,HttpSession session) {
		if(session.getAttribute("cart") == null) {
			session.setAttribute("cart", new Cart());
		}
		Cart cart = (Cart) session.getAttribute("cart");
		ProductDTO productDTO = productService.getProductBySlug(productSlug);
		if (productDTO != null) {
	        cart.addToCart(productDTO);
	        logger.info("Added to cart successfully");
	        
	        int totalItems = cart.getTotalItems();
	        int totalPrice = cart.getTotalPrice();
	        
	        session.setAttribute("totalItems", totalItems);
	        session.setAttribute("totalPrice", totalPrice);
	        
	        JsonObject jsonResponse = new JsonObject();
	        jsonResponse.addProperty("totalItems", totalItems);
	        jsonResponse.addProperty("totalPrice", totalPrice);

	        // Add cart items information
	        
	        jsonResponse.add("cartItems", convertCartItemsToJsonArray(cart));
	        
	        logger.info(jsonResponse.toString());
	        return ResponseEntity.ok(jsonResponse.toString());

	    } else {
	        logger.warn("Product not found");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
	    }
	}
	private JsonArray convertCartItemsToJsonArray(Cart cart) {
	    JsonArray jsonArray = new JsonArray();

	    for (ProductDTO item : cart.getProducts().keySet()) {
	        JsonObject itemJson = new JsonObject();
	        itemJson.addProperty("name", item.getName());
	        itemJson.addProperty("slug", item.getSlug());
	        itemJson.addProperty("image", item.getImage());
	        itemJson.addProperty("quantity", cart.getProducts().get(item));
	        itemJson.addProperty("price", item.getPrice());

	        jsonArray.add(itemJson);
	    }

	    return jsonArray;
	}
*/	
}
