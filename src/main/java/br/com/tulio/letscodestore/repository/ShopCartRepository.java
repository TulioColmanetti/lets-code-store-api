package br.com.tulio.letscodestore.repository;

import br.com.tulio.letscodestore.domain.ShopCart;
import br.com.tulio.letscodestore.dto.ShopCartResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShopCartRepository extends JpaRepository<ShopCart, Long> {

    @Query("SELECT sc FROM ec_shop_cart sc WHERE sc.user.username = ?1 AND sc.status = 0")
    Optional<ShopCart> findShopCartOpenByUsername(String username);

    List<ShopCart> findAllByUser_Username(String username);

    Optional<ShopCart> findShopCartByIdAndUser_Username(Long id, String username);

    @Query("SELECT sc FROM ec_shop_cart sc")
    Page<ShopCartResponseDTO> findAllShopCarts(Pageable pageable);

    @Query("SELECT sc FROM ec_shop_cart sc WHERE sc.user.username = ?1")
    Page<ShopCartResponseDTO> findAllShopCartsByUser_Username(Pageable pageable, String username);
}
