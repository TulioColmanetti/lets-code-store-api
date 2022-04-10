package br.com.tulio.letscodestore.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductListDTO {
    List<Long> productIdList;
}
