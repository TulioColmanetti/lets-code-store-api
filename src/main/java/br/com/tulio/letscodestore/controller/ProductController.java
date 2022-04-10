package br.com.tulio.letscodestore.controller;

import br.com.tulio.letscodestore.domain.Product;
import br.com.tulio.letscodestore.dto.ProductCategoryDTO;
import br.com.tulio.letscodestore.dto.ProductDTO;
import br.com.tulio.letscodestore.resources.ProductResponse;
import br.com.tulio.letscodestore.service.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/store-letscode")
public class ProductController {

    private final ProductServiceImpl productService;

    /* url  ?page=0&size=2&sort=name,asc */
    @GetMapping("/all-products")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> retrieveAllProducts(@PageableDefault(size = 10)
                                                 @SortDefault.SortDefaults({
                                                         @SortDefault(sort = "price", direction = Sort.Direction.ASC),
                                                         @SortDefault(sort = "name", direction = Sort.Direction.DESC),
                                                         @SortDefault(sort = "id", direction = Sort.Direction.ASC)})
                                                         Pageable pageable) {
        Page<Product> products = productService.getAllProducts(pageable);
        // return ResponseEntity.ok(products);
        return ResponseEntity.ok(ProductResponse.fromDomain(products.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveProduct(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(ProductResponse.fromDomain(product));
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    @PostMapping("/register-product")
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDTO request) {
        try {
            Product product = productService.createProduct(request.getName(), request.getPrice());
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDTO request) {
        try {
            productService.updateProduct(id, request);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    @GetMapping("/products/all-categories")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> retrieveAllProductsByCategory(@PageableDefault(size = 10)
                                                 @SortDefault.SortDefaults({
                                                         @SortDefault(sort = "name", direction = Sort.Direction.ASC),
                                                         @SortDefault(sort = "id", direction = Sort.Direction.ASC)})
                                                         Pageable pageable) {
        Page<ProductCategoryDTO> productsByCategory = productService.getAllProductsByCategory(pageable);
        return ResponseEntity.ok(productsByCategory);
//        return ResponseEntity.ok(ProductResponse.fromDomain(products.toList()));
    }

    @GetMapping("/products/all-categories/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> retrieveAllProductsByFilteredCategory(@PathVariable Long id,
                                                           @PageableDefault(size = 10)
                                                           @SortDefault.SortDefaults({
                                                                   @SortDefault(sort = "name", direction = Sort.Direction.ASC),
                                                                   @SortDefault(sort = "id", direction = Sort.Direction.ASC)})
                                                                   Pageable pageable) {
        Page<ProductCategoryDTO> productsByCategory = productService.getAllProductsByFilteredCategory(id, pageable);
        return ResponseEntity.ok(productsByCategory);
//        return ResponseEntity.ok(ProductResponse.fromDomain(products.toList()));
    }

}
