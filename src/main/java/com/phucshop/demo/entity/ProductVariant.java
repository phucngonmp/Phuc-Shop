package com.phucshop.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product_variant")
public class ProductVariant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
    @ManyToOne
    @JoinColumn(name="id_product")
    private Product product;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="id_size")
    private Size size;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="id_color")
    private Color color;
    
    @NotNull
    private int quantity;
}
