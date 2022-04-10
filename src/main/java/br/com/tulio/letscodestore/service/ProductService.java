package br.com.tulio.letscodestore.service;

import br.com.tulio.letscodestore.domain.Product;
import br.com.tulio.letscodestore.dto.ProductCategoryDTO;
import br.com.tulio.letscodestore.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ProductService {

    Page<Product> getAllProducts(Pageable page);

    Product getProductById(Long id);

    Product createProduct(String name, BigDecimal price);

    Product updateProduct(Long id, ProductDTO request);

    void deleteProduct(Long id);

    Page<ProductCategoryDTO> getAllProductsByCategory(Pageable pageable);

    Page<ProductCategoryDTO> getAllProductsByFilteredCategory(Long id, Pageable pageable);

}
