package com.phucshop.demo.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.phucshop.demo.dto.CartItemDTO;
import com.phucshop.demo.dto.ProductDTO;
import com.phucshop.demo.entity.Product;
import com.phucshop.demo.repository.ProductVariantRepository;

public abstract class CommonProductService {

	private final ProductVariantRepository productVariantRepository;

	public CommonProductService(ProductVariantRepository productVariantRepository) {
		this.productVariantRepository = productVariantRepository;
	}

	public ProductDTO convertProductToProductDTO(Product product) {

		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(encodeProductId(product.getId()));
		productDTO.setSlug(product.getSlug());
		productDTO.setName(product.getName());
		productDTO.setCategory(product.getCategory().getName());
		productDTO.setImage(product.getImage());
		productDTO.setPrice(product.getPrice());
		productDTO.setColors(productVariantRepository.findColorsByProduct(product.getId()));
		productDTO.setIsshow(product.getIsshow());

		Map<String, Integer> map = new HashMap<>();

		if (!productDTO.getColors().isEmpty()) {
			// find all size and quantity of first variant based color
			List<Object[]> list = productVariantRepository.findSizeAndQuantityByColorName(productDTO.getColors().get(0),
					product.getId());

			for (Object[] o : list) {
				String size = (String) o[0];
				Integer quantity = (Integer) o[1];
				map.put(size, quantity);
			}
		}

		productDTO.setSizeAndQuantity(map);

		return productDTO;

	}

	public List<ProductDTO> convertListProductsToDTO(List<Product> products) {
		List<ProductDTO> l = new ArrayList<>();
		for (Product p : products) {
			l.add(convertProductToProductDTO(p));
		}
		return l;
	}

	public ProductDTO convertProductOptionalToDTO(Optional<Product> productOptional) {
		// Implement this method to convert a Product entity to a ProductDTO
		if (productOptional.isPresent()) {

			Product product = productOptional.get();

			return convertProductToProductDTO(product);
		} else {
			return null;
		}
	}

	public CartItemDTO convertProductOptionalToCartItemDTO(Optional<Product> productOptional, String size, String color) {
		if (productOptional.isPresent()) {

			Product product = productOptional.get();

			return convertProductToCartItemDTO(product, size, color);
		} else {
			return null;
		}
	}
	
	public CartItemDTO convertProductToCartItemDTO(Product product, String size, String color){
		CartItemDTO item = new CartItemDTO();
		item.setName(product.getName());
		item.setProductId(encodeProductId(product.getId()));
		item.setPrice(product.getPrice());
		item.setImage(product.getImage());
		item.setSlug(product.getSlug());
		item.setColor(color);
		item.setSize(size);
		return item;
	}
	
	
	

	public String encodeProductId(Integer productId) {
		// Chuyển đổi Long sang byte array
		byte[] bytes = Long.toString(productId).getBytes(StandardCharsets.UTF_8);

		// Mã hóa bằng Base64
		String encodedProductId = Base64.getEncoder().encodeToString(bytes);

		return encodedProductId;
	}

	// Giải mã ID sản phẩm
	public Integer decodeProductId(String encodedProductId) {
		// Giải mã từ Base64
		byte[] decodedBytes = Base64.getDecoder().decode(encodedProductId);

		// Chuyển đổi byte array thành String
		String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);

		// Chuyển đổi String thành Long
		Integer productId = Integer.parseInt(decodedString);

		return productId;
	}
	
	

}
