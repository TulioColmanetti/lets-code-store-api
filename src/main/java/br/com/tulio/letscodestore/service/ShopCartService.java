package br.com.tulio.letscodestore.service;

import br.com.tulio.letscodestore.domain.Product;
import br.com.tulio.letscodestore.domain.ShopCart;
import br.com.tulio.letscodestore.dto.ShopCartResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShopCartService {

    Page<ShopCartResponseDTO> getAllShopCart(Pageable pageable);

    ShopCart getShopCartById(Long id);

    ShopCart saveShopCart(List<Long> productIdList);

    void orderShopCart(Long id);

    void deliverShopCart(Long id);

    void finishShopCart(Long id);

    void cancelShopCart(Long id);

    void addProductToShopCart(Long id, Product product);

    void addProductToShopCart(Long id, Product... products);

    void removeProductFromShopCart(Long id, Product product);

    void removeShopCart(Long id);
}
