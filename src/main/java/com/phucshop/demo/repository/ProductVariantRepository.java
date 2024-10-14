package com.phucshop.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.phucshop.demo.entity.Color;
import com.phucshop.demo.entity.Product;
import com.phucshop.demo.entity.ProductVariant;
import com.phucshop.demo.entity.Size;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {

	@Query(value = "SELECT product_variant.* FROM product_variant JOIN product "
			+ "ON product_variant.id_product = product.id WHERE product.slug = :productSlug", nativeQuery = true)
	public List<ProductVariant> findBySlug(@Param("productSlug") String productSlug);

	public List<ProductVariant> findByProduct(Product product);

	@Query(value = "SELECT s.size AS size_name, pv.quantity FROM product_variant pv JOIN color c"
			+ " ON pv.id_color = c.id JOIN size s ON pv.id_size = s.id WHERE c.color = :colorName and pv.id_product = :id", nativeQuery = true)
	// co the dung cach khac de lay object luon do la tao 1 doi tuong khac co 2
	// field la color_name va quantiy
	public List<Object[]> findSizeAndQuantityByColorName(@Param("colorName") String colorName,
			@Param("id") int id_product);

	@Query(value = "SELECT DISTINCT c.color FROM product_variant pv JOIN color c ON pv.id_color = c.id WHERE pv.id_product = :id", nativeQuery = true)
	public List<String> findColorsByProduct(@Param("id") int id_product);

	Optional<ProductVariant> findByColorAndSizeAndProduct(Color color, Size size, Product product);


}
