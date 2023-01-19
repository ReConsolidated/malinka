package io.github.reconsolidated.malinka.loyaltyProduct;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 *  specific class for loyalty product
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LoyaltyProduct {
    @Id
    @GeneratedValue
    private Long id;
    private String name = "default";
    @Column(length = 50000)
    private String fileName = "default";
    private String category = "default";

    private int points = 1;

    public LoyaltyProduct(String name, String fileName, String category, int points) {
        this.name = name;
        this.fileName = fileName;
        this.category = category;
        this.points = points;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;
        LoyaltyProduct other = (LoyaltyProduct) obj;
        return Objects.equals(this.id, other.id);
    }
}
