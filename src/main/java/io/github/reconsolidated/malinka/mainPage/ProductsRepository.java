package io.github.reconsolidated.malinka.mainPage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *  Repository for products
 **/
@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {

    /**
     * get list of products by cetegory
     * @param category
     * @return
     */
    default List<Product> getByCategory(String category) {
        if (category.equals("all")) {
            return findAll();
        }
        List<Product> result = new ArrayList<>();
        for (Product product : findAll()) {
            if (product.getCategory().equals(category)) {
                result.add(product);
            }
        }
        return result;
    }

    /**
     * get promotioncs
     * @return
     */
    default List<Product> getPromotions() {

        List<Product> result = new ArrayList<>();
        for (Product product : findAll()) {
            if (product.isOnSale()) {
                result.add(product);
            }
        }
        return result;
    }

    /**
     * get promotions by category
     * @param category
     * @return
     */
    default List<Product> getPromotionByCategory(String category) {
        List<Product> promotions = getPromotions();

        if (category.equals("all")) {
            return promotions;
        }
        List<Product> result = new ArrayList<>();
        for (Product product : promotions) {
            if (product.getCategory().equals(category)) {
                result.add(product);
            }
        }
        return result;
    }

    /**
     * get by name product
     * @param productName
     * @return
     */
    default Product getByName(String productName) {
        for (Product product : findAll()) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

}
