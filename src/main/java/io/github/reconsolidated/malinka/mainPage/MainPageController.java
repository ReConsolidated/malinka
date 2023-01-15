package io.github.reconsolidated.malinka.mainPage;

import io.github.reconsolidated.malinka.basket.BasketService;
import io.github.reconsolidated.malinka.loyaltyProduct.LoyaltyProduct;
import io.github.reconsolidated.malinka.loyaltyProduct.LoyaltyProductsService;
import io.github.reconsolidated.malinka.user.User;
import io.github.reconsolidated.malinka.orders.OrderService;
import io.github.reconsolidated.malinka.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class MainPageController {
    private final ProductsService productsService;
    private final LoyaltyProductsService loyaltyProductsService;
    private final BasketService basketService;
    private final OrderService orderService;

    private final UserService userService;

    @GetMapping("/")
    public String mainPage(@RequestParam(required = false) String category,
                           @RequestParam(required = false) String promotionCategory,
                           Model model) {

        if (category == null) {
            category = "all";
        }
        if (promotionCategory == null) {
            promotionCategory = "all";
        }

        List<Product> products = productsService.getByCategory(category);
        List<Product> promotions = productsService.getPromotionsByCategory(category);
        List<Product> randomProducts = productsService.getUniqueRandomForMainPage();
        List<LoyaltyProduct> loyaltyProducts = loyaltyProductsService.getLoyaltyProducts();
        User user = userService.getUserByUsername("jkowal");

        model.addAttribute("category", category);
        model.addAttribute("promotionCategory", promotionCategory);
        model.addAttribute("products", products);
        model.addAttribute("promotions", promotions);
        model.addAttribute("loyaltyProducts", loyaltyProducts);
        model.addAttribute("randomProducts", randomProducts);
        model.addAttribute("basketSize", basketService.getNumOfProducts());
        model.addAttribute("basketTotal", String.format("%.2f", basketService.getTotal()) + " zł");
        model.addAttribute("userInfo", String.format("%s %s", user.getName(), user.getSurname()));
        model.addAttribute("userPoints", user.getLoyaltyPoints());
        return "index";
    }

    @GetMapping("/history")
    public String transactionHistory(Model model) {
        model.addAttribute("orders", orderService.getOrders());
        model.addAttribute("basketProducts", basketService.getProductsInBasket());
        model.addAttribute("basketTotal", String.format("%.2f", basketService.getTotal()) + " zł");
        return "transaction_history";
    }


}
