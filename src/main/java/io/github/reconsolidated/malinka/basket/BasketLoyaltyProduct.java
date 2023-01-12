package io.github.reconsolidated.malinka.basket;

import io.github.reconsolidated.malinka.model.LoyaltyProduct;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BasketLoyaltyProduct {
    @GeneratedValue
    @Id
    private Long id;

    private String productName;

    private int quantity;

    @Transient
    private LoyaltyProduct loyaltyProduct = new LoyaltyProduct();

    public String getTotal() {
        return String.valueOf(quantity * loyaltyProduct.getPoints());
    }

    public BasketLoyaltyProduct(LoyaltyProduct product, int quantity) {
        this.loyaltyProduct = product;
        this.productName = product.getName();
        this.quantity = quantity;
    }
}