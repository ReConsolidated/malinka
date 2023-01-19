package io.github.reconsolidated.malinka.reclamation;

import io.github.reconsolidated.malinka.basket.BasketService;
import io.github.reconsolidated.malinka.orders.Order;
import io.github.reconsolidated.malinka.orders.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * controller fo reclamation
 */
@Controller
@AllArgsConstructor
public class ReclamationController {
    private final BasketService basketService;
    private final OrderService orderService;

    /**
     * page for reclamation success
     * @param model
     * @return
     */
    @GetMapping("/reclamation_success")
    public String reclamationSuccess(Model model) {
        model.addAttribute("basket", basketService.getProductsInBasket());
        model.addAttribute("basketTotal", String.format("%.2f", basketService.getTotal()) + " zł");
        return "reclamation_success";
    }

    /**
     * get page for reclamation
     * @param orderId
     * @param model
     * @return
     */
    @GetMapping("/reclamation")
    public String reclamationsPage(@RequestParam("orderId") long orderId, Model model) {
        Order order = orderService.getOrders().stream().filter((o) -> o.getId() == orderId).findFirst().orElseThrow();
        model.addAttribute("order", order);
        model.addAttribute("basketProducts", basketService.getProductsInBasket());
        model.addAttribute("basketTotal", String.format("%.2f", basketService.getTotal()) + " zł");
        return "reclamation";
    }

}
