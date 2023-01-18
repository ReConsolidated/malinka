package io.github.reconsolidated.malinka.orders;

import io.github.reconsolidated.malinka.basket.BasketLoyaltyProduct;
import io.github.reconsolidated.malinka.basket.BasketProduct;
import io.github.reconsolidated.malinka.basket.BasketService;
import io.github.reconsolidated.malinka.user.User;
import io.github.reconsolidated.malinka.delivery.DeliveryMethod;
import io.github.reconsolidated.malinka.delivery.ParcelDelivery;
import io.github.reconsolidated.malinka.delivery.SelfPickupDelivery;
import io.github.reconsolidated.malinka.delivery.StandardDelivery;
import io.github.reconsolidated.malinka.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static io.github.reconsolidated.malinka.utils.Constants.LOYALTY_POINTS_MULTIPLIER;

@Controller
@AllArgsConstructor
public class OrderController {
    private final BasketService basketService;
    private final OrderService orderService;

    private final UserService userService;
    @GetMapping("/shipment")
    public String shipment(
            RedirectAttributes redirectAttributes,
            Model model) {

        List<BasketProduct> basketProducts = basketService.getProductsInBasket();
        List<BasketLoyaltyProduct> loyaltyInBasket = basketService.getLoyaltyProductsInBasket();

        if(basketProducts.isEmpty() && loyaltyInBasket.isEmpty()) {
            return "redirect:/";
        }
        User user = userService.getUserByUsername("jkowal");

        model.addAttribute("basketProducts", basketProducts);
        model.addAttribute("basketTotal", String.format("%.2f", basketService.getTotal()) + " zł");
        model.addAttribute("basketSize", basketService.getNumOfProducts());
        model.addAttribute("userInfo", String.format("%s %s", user.getName(), user.getSurname()));
        model.addAttribute("userPoints", user.getLoyaltyPoints());
        return "shipment_select";
    }

    @PostMapping("/select_shipment")
    public String selectShipment(
            @RequestParam(name="address", required = false) String address,
            @RequestParam(name="city", required = false) String city,
            @RequestParam(name="street", required = false) String street,
            @RequestParam(name="local_num", required = false) String localNum,
            @RequestParam(name="select-parcel-locker", required = false) String selectParcelLocker,
            @RequestParam(name="pickup-time", required = false) String pickupTime,
            @RequestParam(name= "shipment_type", required = false) String shipmentType,
            RedirectAttributes redirectAttributes) {
        orderService.getCurrentOrder().setAddress(address);
        orderService.getCurrentOrder().setCity(city);
        orderService.getCurrentOrder().setStreet(street);
        orderService.getCurrentOrder().setLocalNumber(localNum);
        orderService.getCurrentOrder().setSelectParcelLocker(selectParcelLocker);
        orderService.getCurrentOrder().setPickupTime(pickupTime);

        if (shipmentType.equals("standard")) {
            orderService.getCurrentOrder().setDeliveryMethod(DeliveryMethod.STANDARD);
            orderService.getCurrentOrder().setDelivery(new StandardDelivery(
                    address, city, street, localNum
            ));
        } else if (shipmentType.equals("parcel")) {
            orderService.getCurrentOrder().setDeliveryMethod(DeliveryMethod.PARCEL);
            orderService.getCurrentOrder().setDelivery(new ParcelDelivery(selectParcelLocker));
        } else {
            orderService.getCurrentOrder().setDeliveryMethod(DeliveryMethod.SELF_PICKUP);
            orderService.getCurrentOrder().setDelivery(new SelfPickupDelivery(pickupTime));
        }

        return "redirect:/payment";
    }

    @GetMapping("/payment")
    public String payment(Model model) {
        List<BasketProduct> basketProducts = basketService.getProductsInBasket();
        List<BasketLoyaltyProduct> loyaltyInBasket = basketService.getLoyaltyProductsInBasket();

        if(basketProducts.isEmpty() && loyaltyInBasket.isEmpty()) {
            return "redirect:/";
        }
        User user = userService.getUserByUsername("jkowal");

        model.addAttribute("basketProducts", basketProducts);
        model.addAttribute("basketTotal", String.format("%.2f", basketService.getTotal()) + " zł");
        model.addAttribute("basketSize", basketService.getNumOfProducts());
        model.addAttribute("userInfo", String.format("%s %s", user.getName(), user.getSurname()));
        model.addAttribute("userPoints", user.getLoyaltyPoints());
        model.addAttribute("deliveryType", orderService.getCurrentOrder().getDeliveryMethod().name());
        return "payment_select";
    }

    @PostMapping("/payment_info")
    public String paymentInfo(@RequestParam(name="pay") String isPaid,
                              RedirectAttributes redirectAttributes) {

        if (isPaid.equals("Tak")) {
            Order current = orderService.getCurrentOrder();
            Double total = basketService.getTotal();
            int loyaltyTotal = basketService.getLoyaltyTotal();

            int additionalLoyaltyPoints = (int) (total * LOYALTY_POINTS_MULTIPLIER);


            orderService.getCurrentOrder().setPaymentSuccessful(true);
            orderService.saveOrder();
            basketService.clearBasket();
            User user = userService.getUserByUsername("jkowal");
            user.setLoyaltyPoints(user.getLoyaltyPoints() + additionalLoyaltyPoints - loyaltyTotal);
            return "redirect:/success";
        } else {
            redirectAttributes.addAttribute("orderNum", String.valueOf(orderService.getCurrentOrder().getId()));
            orderService.getCurrentOrder().setPaymentSuccessful(true);
            orderService.saveOrder();
            basketService.clearBasket();
            return "redirect:/fail";
        }
    }

    @PostMapping("/pay_for_order")
    public String payForOrder(@RequestParam(name="payment") String paymentMethod,
                              Model model,
                              RedirectAttributes redirectAttributes) {

        orderService.getCurrentOrder().setPaymentMethod(paymentMethod);
        orderService.getCurrentOrder().setCost(String.format("%.2f",basketService.getTotal()) + " zł");
        orderService.getCurrentOrder().setAmount(basketService.getProductsInBasket().size());
        orderService.getCurrentOrder().getProducts().addAll(basketService.getProductsInBasket());


        List<BasketProduct> basketProducts = basketService.getProductsInBasket();
        User user = userService.getUserByUsername("jkowal");

        model.addAttribute("basketProducts", basketService.getProductsInBasket());
        model.addAttribute("basketTotal", String.format("%.2f", basketService.getTotal()) + " zł");
        model.addAttribute("userInfo", String.format("%s %s", user.getName(), user.getSurname()));
        model.addAttribute("userPoints", user.getLoyaltyPoints());

        return "payment_try";
    }

    @GetMapping("/success")
    public String success(Model model) {
        List<BasketProduct> basketProducts = basketService.getProductsInBasket();
        User user = userService.getUserByUsername("jkowal");

        model.addAttribute("basketProducts", basketProducts);
        model.addAttribute("basketTotal", String.format("%.2f", basketService.getTotal()) + " zł");
        model.addAttribute("order", orderService.getCurrentOrder());
        model.addAttribute("userInfo", String.format("%s %s", user.getName(), user.getSurname()));
        model.addAttribute("userPoints", user.getLoyaltyPoints());

        return "payment_success";
    }

    @GetMapping("/fail")
    public String fail(@RequestParam(name="orderNum") String orderNumber,
                       Model model) {
        User user = userService.getUserByUsername("jkowal");

        model.addAttribute("basketProducts", basketService.getProductsInBasket());
        model.addAttribute("basketTotal", String.format("%.2f", basketService.getTotal()) + " zł");
        model.addAttribute("userInfo", String.format("%s %s", user.getName(), user.getSurname()));
        model.addAttribute("userPoints", user.getLoyaltyPoints());
        model.addAttribute("orderNum", orderNumber);

        return "no_payment";
    }
}
