package br.com.tulio.letscodestore.controller;

import br.com.tulio.letscodestore.domain.ShopCart;
import br.com.tulio.letscodestore.dto.ProductListDTO;
import br.com.tulio.letscodestore.dto.ShopCartResponseDTO;
import br.com.tulio.letscodestore.service.ShopCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/store-letscode/shop-cart")
@RequiredArgsConstructor
public class ShopCartController {

    private final ShopCartService shopCartService;

    @GetMapping
    public ResponseEntity<Page<ShopCartResponseDTO>> getAllShopCart(Pageable pageable){
        return ResponseEntity.ok().body(shopCartService.getAllShopCart(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopCart> getAllShopCart(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(shopCartService.getShopCartById(id));
    }

    @PostMapping
    public ResponseEntity<?> saveShopCart(@RequestBody ProductListDTO productListDTO){
        return ResponseEntity.ok().body(shopCartService.saveShopCart(productListDTO.getProductIdList()));
    }

    @PutMapping("order/{id}")
    public ResponseEntity<?> orderShopCart(@PathVariable("id") Long id){
        shopCartService.orderShopCart(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("deliver/{id}")
    public ResponseEntity<?> deliverShopCart(@PathVariable("id") Long id){
        shopCartService.deliverShopCart(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("finish/{id}")
    public ResponseEntity<?> finishShopCart(@PathVariable("id") Long id){
        shopCartService.finishShopCart(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("cancel/{id}")
    public ResponseEntity<?> cancelShopCart(@PathVariable("id") Long id){
        shopCartService.cancelShopCart(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeShopCart(@PathVariable("id") Long id){
        shopCartService.removeShopCart(id);
        return ResponseEntity.noContent().build();
    }
};