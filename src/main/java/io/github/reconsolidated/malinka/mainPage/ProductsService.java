package io.github.reconsolidated.malinka.mainPage;

import io.github.reconsolidated.malinka.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class ProductsService {
    private final ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<Product> getByCategory(String category) {
        return productsRepository.getByCategory(category);
    }

    public List<Product> getPromotionsByCategory(String category) {
        return productsRepository.getPromotionByCategory(category);
    }

    public Product getByName(String productName) {
        return productsRepository.getByName(productName);
    }

    public Product getRandomProduct() {
        Random random = new Random();
        List<Product> products = productsRepository.findAll();
        System.out.println(products);
        int randomIndex = random.nextInt(products.size());
        return products.get(randomIndex);
    }

    public List<Product> getUniqueRandomForMainPage() {
       Set<Product> uniqueProducts = new HashSet<Product>();
       while (uniqueProducts.size() < Constants.RANDOM_PRODUCTS_FOR_MAIN_PAGE) {
           uniqueProducts.add(getRandomProduct());
       }

       return uniqueProducts.stream().toList();
    }

    public List<Product> getAll() {
        return productsRepository.findAll();
    }
}
