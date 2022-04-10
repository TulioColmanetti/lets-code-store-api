package br.com.tulio.letscodestore.repository;

import br.com.tulio.letscodestore.domain.Product;
import br.com.tulio.letscodestore.dto.ProductCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /*ordenando pelo nome da categoria*/
    @Query(
            value = "select pc.name as categoryName, p.nm_product as name, p.price " +
                    "from ec_product_category pc " +
                    "join ec_product_category_aux pca ON pca.product_id = pc.id " +
                    "join ec_product p ON p.id = pca.category_id " +
                    "order by pc.name", nativeQuery = true
    )
    Page<ProductCategoryDTO> findProductsByCategory(Pageable pageable);

    /*filtrando produtos de uma categoria*/
    @Query(
            value = "select pc.name as categoryName, p.nm_product as name, p.price " +
                    "from ec_product_category pc " +
                    "join ec_product_category_aux pca ON pca.product_id = pc.id " +
                    "join ec_product p ON p.id = pca.category_id " +
                    "where pc.id = ?1", nativeQuery = true
    )
    Page<ProductCategoryDTO> findProductsByFilteredCategory(Long id, Pageable pageable);
}