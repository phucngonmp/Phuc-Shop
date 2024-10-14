package com.phucshop.demo.api;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Optional;

import org.hibernate.metamodel.mapping.ordering.ast.OrderingExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phucshop.demo.dto.CartItemDTO;
import com.phucshop.demo.entity.Cart;
import com.phucshop.demo.entity.Color;
import com.phucshop.demo.entity.Order;
import com.phucshop.demo.entity.OrderDetail;
import com.phucshop.demo.entity.OrderInfo;
import com.phucshop.demo.entity.Product;
import com.phucshop.demo.entity.ProductVariant;
import com.phucshop.demo.entity.Size;
import com.phucshop.demo.entity.User;
import com.phucshop.demo.repository.ColorRepository;
import com.phucshop.demo.repository.OrderDetailRepository;
import com.phucshop.demo.repository.OrderInfoRepository;
import com.phucshop.demo.repository.OrderRepository;
import com.phucshop.demo.repository.ProductRepository;
import com.phucshop.demo.repository.ProductVariantRepository;
import com.phucshop.demo.repository.SizeRepository;
import com.phucshop.demo.service.ProductService;
import com.phucshop.demo.service.ProductVariantService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.Builder;

@RestController
public class Crud {
	
	private final ProductRepository productRepository;
	private final ProductService productService;
	private final OrderInfoRepository orderInfoRepository;
	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final ProductVariantRepository productVariantRepository;
	private final SizeRepository sizeRepository;
	private final ColorRepository colorRepository;
	private static final Logger log = LoggerFactory.getLogger(Crud.class);


    Crud(ProductRepository productRepository, OrderInfoRepository orderInfoRepository, 
    		OrderRepository orderRepository,OrderDetailRepository orderDetailRepository,
    		ProductVariantRepository productVariantRepository, ColorRepository colorRepository,
    		SizeRepository sizeRepository, ProductService productService) {
        this.productRepository = productRepository;
		this.orderInfoRepository = orderInfoRepository;
		this.orderRepository = orderRepository;
		this.orderDetailRepository = orderDetailRepository;
		this.productVariantRepository= productVariantRepository;
		this.colorRepository = colorRepository;
		this.sizeRepository = sizeRepository;
		this.productService = productService;
		
    }


	@DeleteMapping("/api/product/delete/{slug}")
	@Transactional
	public ResponseEntity<String> deleteProduct(@PathVariable String slug) {
		
		productRepository.deleteBySlug(slug);

		return ResponseEntity.ok("success");
		
	}
	
	@PostMapping("/api/order/create-order-info")
	public ResponseEntity<String> createInfo(@RequestParam String name,HttpSession session, 
			@RequestParam String address, @RequestParam String phone) {
		
		
		Cart cart = (Cart) session.getAttribute("cart");
		
		// luu order info
		OrderInfo orderInfo = new OrderInfo();
        orderInfo.setName(name);
        orderInfo.setAddress(address);
        orderInfo.setPhone(phone);
        
        // luu order
        Order order = new Order();
        order.setOderInfo(orderInfo);
        order.setPrice((Integer) session.getAttribute("totalPrice"));
        order.setStatus((byte) 0);
        order.setUser((User)session.getAttribute("loggedInUser"));
        order.setCreate_at(LocalDate.now());
        
        
		
		orderInfoRepository.save(orderInfo);
		orderRepository.save(order);
		
		// luu productdetail
        for(CartItemDTO cartItem : cart.getItems().keySet()) { // class cart chua 1 map cartItem(item : soluong cua item do)
        	
        	OrderDetail orderDetail = new OrderDetail();
        	
        	orderDetail.setOrder(order);
        	int sl = cart.getItems().get(cartItem);
            orderDetail.setQuantity(sl);
            
            ProductVariant pv = getProductVariant(cartItem.getColor(), cartItem.getSize(), decodeBase64Id(cartItem.getProductId()));
            orderDetail.setProductVariant(pv);
            
            Optional<Product> o = productRepository.findById(decodeBase64Id(cartItem.getProductId()));
            Product p = o.get();
            
            
            // tang so luong da ban o product
            p.setSold(p.getSold() + sl);
            productRepository.save(p);
            // giam so luong ton kho o product variant
            pv.setQuantity(pv.getQuantity()- sl);
            productVariantRepository.save(pv);
            
            
            

            orderDetailRepository.save(orderDetail);
        }
        
        session.removeAttribute("cart");
		
		return ResponseEntity.ok("success");
	}
	

	

	
	private int decodeBase64Id(String base64Id) {
        // Decode the Base64-encoded string
        byte[] decodedBytes = Base64.getDecoder().decode(base64Id);

        // Parse the decoded bytes as an integer
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
        return Integer.parseInt(decodedString);
    }
	
	private ProductVariant getProductVariant(String color, String size, int id) {
		ProductVariant p = new ProductVariant();
		Color c = colorRepository.findByName(color).get(0);
		Size s = sizeRepository.findByName(size).get(0);
		
		
		Optional<Product> o = productRepository.findById(id);
		Product pro = o.get();
		
		
		Optional<ProductVariant> o1 = productVariantRepository.findByColorAndSizeAndProduct(c, s, pro);
		p = o1.get();
		
		
		return p;
		
	}
	
    

}
