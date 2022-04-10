package br.com.tulio.letscodestore.service;

import br.com.tulio.letscodestore.domain.Product;
import br.com.tulio.letscodestore.dto.ProductCategoryDTO;
import br.com.tulio.letscodestore.dto.ProductDTO;
import br.com.tulio.letscodestore.exception.NotFoundException;
import br.com.tulio.letscodestore.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @PreAuthorize("hasRole('ROLE_USER')")
    @Transactional
    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("product"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @Override
    public Product createProduct(String name, BigDecimal price) {

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);

        return productRepository.save(product);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @Override
    public Product updateProduct(Long id, ProductDTO request) {
        Product productUpdate = getProductById(id);
        BeanUtils.copyProperties(request, productUpdate);
        return productRepository.save(productUpdate);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @Override
    public void deleteProduct(Long id) {
        Product productToDelete = getProductById(id);
        productRepository.delete(productToDelete);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Transactional
    @Override
    public Page<ProductCategoryDTO> getAllProductsByCategory(Pageable pageable) {
        return productRepository.findProductsByCategory(pageable);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Transactional
    @Override
    public Page<ProductCategoryDTO> getAllProductsByFilteredCategory(Long id, Pageable pageable) {
        return productRepository.findProductsByFilteredCategory(id, pageable);
    }

}