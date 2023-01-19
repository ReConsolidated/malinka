package io.github.reconsolidated.malinka.loyaltyProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;

/**
 *  this is repository for connecting to database and localproducts
 **/
@Repository
public interface LoyaltyProductsRepository extends JpaRepository<LoyaltyProduct, Long> {

    List<LoyaltyProduct> getByCategory(String category);

    LoyaltyProduct getByName(String productName);

    default LoyaltyProduct getRandomLoyaltyProduct() {
        Random random = new Random();
        List<LoyaltyProduct> loyaltyProductList = findAll();
        int randomIndex = random.nextInt(loyaltyProductList.size());
        return loyaltyProductList.get(randomIndex);
    }
}
