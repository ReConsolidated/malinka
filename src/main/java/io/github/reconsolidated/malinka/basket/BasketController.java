package io.github.reconsolidated.malinka.basket;

import io.github.reconsolidated.malinka.loyaltyProduct.LoyaltyProductsService;
import io.github.reconsolidated.malinka.mainPage.Product;
import io.github.reconsolidated.malinka.mainPage.ProductsService;
import io.github.reconsolidated.malinka.loyaltyProduct.LoyaltyProduct;
import io.github.reconsolidated.malinka.user.User;
import io.github.reconsolidated.malinka.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
public class BasketController {
    private final BasketService basketService;
    private final UserService userService;
    private final ProductsService productsService;
    private final LoyaltyProductsService loyaltyProductsService;

    @PostMapping("/add_to_basket")
    public String addToBasket(@RequestParam(name="category") String category,
                              @RequestParam(name="productName") String productName,
                              @RequestParam(name="quantity") int quantity,
                              RedirectAttributes redirectAttributes) {
        if (category == null) {
            category = "all";
        }

        Product product = productsService.getByName(productName);
        basketService.addProduct(product, quantity);
        redirectAttributes.addAttribute("category", category);
        return "redirect:/";
    }

    @PostMapping("/add_loyalty_to_basket")
    public String addLoyaltyToBasket(@RequestParam(name="category") String category,
                                     @RequestParam(name="productName") String productName,
                                     @RequestParam(name="quantity") int quantity,
                                     @RequestParam(name="points") int points,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {

        if (category == null) {
            category = "all";
        }

        LoyaltyProduct product = loyaltyProductsService.getByName(productName);

        int usedPoints = basketService.getLoyaltyTotal();

        if(product.getPoints() * quantity + usedPoints > points) {
            model.addAttribute("product", String.format("%s %d pkt", product.getName(), product.getPoints()));
            model.addAttribute("userPoints", String.format("%d pkt", points));
            return "basket_error_points";
        }

        basketService.addLoyaltyProduct(product, quantity);
        redirectAttributes.addAttribute("category", category);
        return "redirect:/";
    }


    @GetMapping("/basket")
    public String basketPage(Model model,
                             RedirectAttributes redirectAttributes) {

        List<BasketProduct> basketProducts = basketService.getProductsInBasket();
        List<BasketLoyaltyProduct> basketLoyaltyProducts = basketService.getLoyaltyProductsInBasket();

        if(basketProducts.isEmpty() && basketLoyaltyProducts.isEmpty()) {
            return "basket_empty";
        }
        User user = userService.getUserByUsername("jkowal");

        model.addAttribute("basketProducts", basketProducts);
        model.addAttribute("basketLoyaltyProducts", basketLoyaltyProducts);
        model.addAttribute("basketTotal", String.format("%.2f", basketService.getTotal()) + " zł");
        model.addAttribute("loyaltyTotal", basketService.getLoyaltyTotal() + " pkt.");
        model.addAttribute("basketSize", basketService.getNumOfProducts());
        model.addAttribute("userInfo", String.format("%s %s", user.getName(), user.getSurname()));
        model.addAttribute("userPoints", user.getLoyaltyPoints());
        return "basket";
    }

    @PostMapping("/update_basket_amount")
    public String updateBasketAmount(@RequestParam(name="productName") String productName,
                                     @RequestParam(name="quantity") int quantity,
                                     @RequestParam(name="added", required = false) Integer added,
                                     RedirectAttributes redirectAttributes) {
        if (added == null) added = 0;
        for (BasketProduct bp : basketService.getProductsInBasket()) {
            if (bp.getProduct().getName().equals(productName)) {
                if (quantity + added <= 0) {
                    basketService.removeProduct(bp.getProduct());
                } else {
                    bp.setQuantity(quantity + added);
                }
                return "redirect:/basket";
            }
        }
        return "redirect:/basket";
    }

    @PostMapping("/update_basket_loyalty_amount")
    public String updateBasketLoyaltyAmount(@RequestParam(name="productName") String productName,
                                            @RequestParam(name="quantity") int quantity,
                                            @RequestParam(name="points") int points,
                                            @RequestParam(name="added", required = false) Integer added,
                                            RedirectAttributes redirectAttributes,
                                            Model model) {

        if (added == null) added = 0;
        List<BasketLoyaltyProduct> productsInBasket = basketService.getLoyaltyProductsInBasket();

        for (BasketLoyaltyProduct bp : productsInBasket) {
            if (bp.getLoyaltyProduct().getName().equals(productName)) {
                if (quantity + added <= 0) {
                    basketService.removeLoyaltyProduct(bp.getLoyaltyProduct());
                } else {
                    bp.setQuantity(quantity + added);
                    int totalPoints = basketService.getLoyaltyTotal();

                    if (totalPoints > points) {
                        bp.setQuantity(quantity);
                        model.addAttribute("product", String.format("%s %d pkt", bp.getLoyaltyProduct().getName(), bp.getLoyaltyProduct().getPoints()));
                        model.addAttribute("userPoints", String.format("%d pkt", points));
                        return "basket_error_points";
                    }
                }
                return "redirect:/basket";
            }
        }
        return "redirect:/basket";
    }
}
