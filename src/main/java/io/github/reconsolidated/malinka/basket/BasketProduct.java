package io.github.reconsolidated.malinka.basket;

import io.github.reconsolidated.malinka.mainPage.Product;
import jakarta.persistence.*;
import lombok.*;

/**
 * product in basket with quantity
 **/
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Produkt_Koszyk")
public class BasketProduct {
    @GeneratedValue
    @Id
    private Long id;
    @Column(name="ProduktIdProduktu")
    private Integer productId;
    @Column(name="KoszykIdKoszyka")
    private Integer basketId;
    @Column(name="IloscProduktu")
    private int quantity;
    @Transient
    private String productName;
    @Transient
    private Product product = new Product();

    /**
     * get total from products price and quantity
     * @return
     */
    public String getTotal() {
        return String.format("%.2f", product.getPrice() * quantity);
    }

    public BasketProduct(Product product, int quantity) {
        this.product = product;
        this.productName = product.getName();
        this.quantity = quantity;
    }
}
