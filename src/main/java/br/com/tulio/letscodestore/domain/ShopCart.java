package br.com.tulio.letscodestore.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="ec_shop_cart")
public class ShopCart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name="ec_product_shop_cart_aux",
            joinColumns=
            @JoinColumn(name="shop_cart_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="product_id", referencedColumnName="id")
    )
    private List<Product> products;

    @ManyToOne(targetEntity = User.class)
    private User user;

    private ShopCartStatus status;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;
}
