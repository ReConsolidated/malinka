package io.github.reconsolidated.malinka.orders;

import io.github.reconsolidated.malinka.basket.BasketService;
import io.github.reconsolidated.malinka.mainPage.ProductsService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private final BasketService basketService;
    private final List<Order> ordersHistory = new ArrayList<>();
    @Autowired
    private final ProductsService productsService;

    @Getter
    private Order currentOrder = new Order();

    @Autowired
    public OrderService(@Autowired BasketService basketService,@Autowired ProductsService productsService) {
        this.basketService = basketService;
        this.productsService = productsService;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        for (int i = 0; i<3; i++) {
            for (int j = 0; j<10; j++) {
                basketService.addProduct(productsService.getRandomProduct(), (int)(Math.random() * 10));
            }
            currentOrder.setAddress("Plac grunwaldzki 6");
            currentOrder.setCity("Wrocław");
            currentOrder.setStreet("Plac grunwaldzki");
            currentOrder.setLocalNumber("6");
            currentOrder.setSelectParcelLocker("Kraków");
            currentOrder.setPickupTime(LocalDateTime.now().minusDays(i).toString());
            currentOrder.setPaymentMethod("Za pobraniem");
            currentOrder.setPaymentSuccessful(true);
            currentOrder.setCost(String.format("%.2f", basketService.getTotal()) + " zł");
            currentOrder.setAmount(basketService.getProductsInBasket().size());
            currentOrder.setDate(LocalDateTime.now().minusDays(i).minusMinutes(i * 8L));
            currentOrder.getProducts().addAll(basketService.getProductsInBasket());
            currentOrder.getLoyaltyProducts().addAll(basketService.getLoyaltyProductsInBasket());
            saveOrder();
            basketService.clearBasket();
        }
    }

//    @PostConstruct
    public void fillOldOrders() {

    }
    public void saveOrder() {
        currentOrder.setDate(LocalDateTime.now());
        ordersHistory.add(currentOrder);
        currentOrder = new Order();
    }

    public List<Order> getOrders() {
        return ordersHistory;
    }
}
