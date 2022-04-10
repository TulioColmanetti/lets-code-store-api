package br.com.tulio.letscodestore.dto;

import br.com.tulio.letscodestore.domain.ShopCartStatus;

import java.util.List;

public interface ShopCartResponseDTO {

    Long getId();
    ShopCartStatus getStatus();
    UserResponseDTO getUser();
    List<ProductResponseDTO> getProducts();
}
