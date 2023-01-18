package io.github.reconsolidated.malinka.mainPage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="IdProduktu")
    private Long generatedId;
    @Column(name="NazwaProduktu")
    private String name = "default";
    @Column(name="AdresZdjecia", length = 50000)
    private String fileName = "default";
    @Column(name="Cena")
    private double price = 0.01;
//    @Column(name="RodzajCeny")
//    private Integer priceType = 0;
    @Column(name="Kategoria")
    private String category = "default";
    @Column(name="CreatedAt")
    private Long createdAtTimestamp;
    @Column(name="UpdatedAt")
    private Long updatedAtTimestamp;

    @Transient
    private Long id;
    @Column(name="RodzajCeny")
    private String priceTag = "default";
    @Column(name="CzyPromocja")
    private boolean isOnSale = false;

    public Product(String name, String fileName, String category, String priceTag, double price, boolean isOnSale) {
        this.name = name;
        this.fileName = fileName;
        this.category = category;
        this.priceTag = priceTag;
        this.price = price;
        this.isOnSale = isOnSale;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;

        return Objects.equals(this.id, other.id);
    }
}
