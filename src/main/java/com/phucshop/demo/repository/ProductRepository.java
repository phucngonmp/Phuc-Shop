package com.phucshop.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.phucshop.demo.entity.Category;
import com.phucshop.demo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
//	@Query("SELECT p.name, p.price, p.image, c.name FROM product p JOIN category c ON p.id_category = c.id")
//    List<ProductDTO> findProductsWithCategoryNames(); // Adjust return type according to your needs
	Optional<Product> findBySlug(String slug);
	void deleteBySlug(String slug);
	
//	@Query("SELECT p FROM Product p JOIN p.category c WHERE c.name = :categoryName") // nay la jpql nen Product o day la Object va category la field khong phai la table
//	List<Product> findByCategoryName(@Param("categoryName")String categoryName); // @param nay la de cai parameter o day map voi cai =: o @query

	
	@Query(value = "SELECT p.id, p.price, p.name, p.image, p.slug, p.sold, p.id_category, p.isshow FROM product p "
			+ "INNER JOIN category c ON p.id_category = c.id WHERE c.name  = :categoryName", nativeQuery = true)
	List<Product> findByCategoryName(@Param("categoryName") String categoryName);


}