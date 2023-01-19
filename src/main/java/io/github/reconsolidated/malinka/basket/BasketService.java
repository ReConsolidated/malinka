package io.github.reconsolidated.malinka.basket;

import io.github.reconsolidated.malinka.mainPage.Product;
import io.github.reconsolidated.malinka.loyaltyProduct.LoyaltyProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 This service is responsible for basket management
 **/
@Service
public class BasketService {
    private List<BasketProduct> productsInBasket = new ArrayList<>();
    private List<BasketLoyaltyProduct> loyaltyProductsInBasket = new ArrayList<>();
    private final BasketProductRepository basketProductRepository;

    /**
     * constructor with dependency injection
     * @param basketProductRepository
     */
    @Autowired
    public BasketService(BasketProductRepository basketProductRepository) {
        this.basketProductRepository = basketProductRepository;
    }

    /**
     * method to add products
     * @param product product to add
     * @param quantity quantity for this product
     */
    public void addProduct(Product product, int quantity) {
        for (BasketProduct basketProduct : productsInBasket) {
            if (basketProduct.getProduct().getName().equals(product.getName())) {
                basketProduct.setQuantity(basketProduct.getQuantity() + quantity);
                return;
            }
        }
        BasketProduct basketProduct = new BasketProduct(product, quantity);
        productsInBasket.add(basketProduct);
        basketProductRepository.save(basketProduct);
    }

    /**
     * remove product form service
     * @param product product to remove
     */
    public void removeProduct(Product product) {
        for (BasketProduct basketProduct : productsInBasket) {
            if (basketProduct.getProduct().getName().equals(product.getName())) {
                productsInBasket.remove(basketProduct);
                return;
            }
        }
        basketProductRepository.delete(basketProductRepository.findById(product.getId()).orElse(new BasketProduct()));
    }

    /**
     * function to get all products in basket
     * @return
     */
    public List<BasketProduct> getProductsInBasket() {
        return productsInBasket;
    }

    /**
     * add loyatlty product
     * @param product product to add
     * @param quantity quantity for this product
     */
    public void addLoyaltyProduct(LoyaltyProduct product, int quantity) {
        for (BasketLoyaltyProduct basketLoyaltyProduct : loyaltyProductsInBasket) {
            if (basketLoyaltyProduct.getLoyaltyProduct().getName().equals(product.getName())) {
                basketLoyaltyProduct.setQuantity(basketLoyaltyProduct.getQuantity() + quantity);
                return;
            }
        }
        loyaltyProductsInBasket.add(new BasketLoyaltyProduct(product, quantity));
    }

    /**
     * remove product from loyalty
     * @param product product to remove
     */
    public void removeLoyaltyProduct(LoyaltyProduct product) {
        for (BasketLoyaltyProduct basketLoyaltyProduct: loyaltyProductsInBasket) {
            if (basketLoyaltyProduct.getLoyaltyProduct().getName().equals(product.getName())) {
                loyaltyProductsInBasket.remove(basketLoyaltyProduct);
                return;
            }
        }
    }

    /**
     * get all loyalty products form basket
     * @return
     */
    public List<BasketLoyaltyProduct> getLoyaltyProductsInBasket() {
        return loyaltyProductsInBasket;
    }

    /**
     * clear basket
     */
    public void clearBasket() {
        productsInBasket.clear();
        loyaltyProductsInBasket.clear();
    }

    /**
     * get total price for products
     * @return
     */
    public double getTotal() {
        double total = 0;
        for (BasketProduct basketProduct : productsInBasket) {
            total += basketProduct.getProduct().getPrice() * basketProduct.getQuantity();
        }
        return total;
    }

    /**
     * get total points from loaylty
     * @return
     */
    public int getLoyaltyTotal() {
        int total = 0;
        for (BasketLoyaltyProduct basketProduct : loyaltyProductsInBasket) {
            total += basketProduct.getTotal();
        }
        return total;
    }

    /**
     * get number of products from basket
     * @return
     */
    public int getNumOfProducts() {
        int total = 0;
        for (BasketProduct basketProduct : productsInBasket) {
            total += basketProduct.getQuantity();
        }
        for (BasketLoyaltyProduct basketLoyaltyProduct: loyaltyProductsInBasket) {
            total += basketLoyaltyProduct.getQuantity();
        }
        return total;
    }
}
