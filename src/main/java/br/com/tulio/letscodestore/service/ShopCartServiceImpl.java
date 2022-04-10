package br.com.tulio.letscodestore.service;

import br.com.tulio.letscodestore.domain.Product;
import br.com.tulio.letscodestore.domain.ShopCart;
import br.com.tulio.letscodestore.domain.ShopCartStatus;
import br.com.tulio.letscodestore.dto.ShopCartResponseDTO;
import br.com.tulio.letscodestore.exception.NotFoundException;
import br.com.tulio.letscodestore.repository.ProductRepository;
import br.com.tulio.letscodestore.repository.ShopCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopCartServiceImpl implements ShopCartService {

    private final ShopCartRepository shopCartRepository;
    private final ProductRepository productRepository;

    @Override
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Page<ShopCartResponseDTO> getAllShopCart(Pageable pageable) {
        return shopCartRepository.findAllShopCarts(pageable);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ShopCart getShopCartById(Long id) {
        return shopCartRepository.findById(id).orElseThrow(() -> new NotFoundException("shop cart"));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public ShopCart saveShopCart(List<Long> productIdList) {
        ShopCart shopCart = null;
        List<Product> products = productRepository.findAllById(productIdList);
        shopCart = ShopCart.builder()
                .user(null)
                .products(products)
                .status(ShopCartStatus.ABERTO)
                .build();
        return shopCartRepository.save(shopCart);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public void orderShopCart(Long id) {
        ShopCart shopCart = updateStatus(getShopCartById(id),
                ShopCartStatus.REALIZADO);
        shopCartRepository.saveAndFlush(shopCart);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deliverShopCart(Long id) {
        ShopCart shopCart = updateStatus(getShopCartById(id),
                ShopCartStatus.ENVIADO);
        shopCartRepository.saveAndFlush(shopCart);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void finishShopCart(Long id) {
        ShopCart shopCart = updateStatus(getShopCartById(id),
                ShopCartStatus.CONCLUIDO);
        shopCartRepository.saveAndFlush(shopCart);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void cancelShopCart(Long id) {
        ShopCart shopCart = updateStatus(getShopCartById(id),
                ShopCartStatus.CANCELADO);
        shopCartRepository.saveAndFlush(shopCart);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public void addProductToShopCart(Long id, Product product) {
        ShopCart shopCart = getShopCartById(id);
        shopCart.getProducts().add(product);
        shopCartRepository.saveAndFlush(shopCart);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public void addProductToShopCart(Long id, Product... products) {
        ShopCart shopCart = getShopCartById(id);
        shopCart.getProducts().addAll(List.of(products));
        shopCartRepository.saveAndFlush(shopCart);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public void removeProductFromShopCart(Long id, Product product) {
        ShopCart shopCart = getShopCartById(id);
        List<Product> products = shopCart.getProducts().stream()
                .filter(currProduct -> !product.getName().equals(currProduct.getName()))
                .collect(Collectors.toList());
        shopCart.setProducts(products);
        shopCartRepository.saveAndFlush(shopCart);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void removeShopCart(Long id) {
        getShopCartById(id);
        shopCartRepository.deleteById(id);
    }

    private ShopCart updateStatus(ShopCart shopCart, ShopCartStatus newStatus){
        shopCart.setStatus(newStatus);
        return shopCart;
    }

}
