package io.github.reconsolidated.malinka.orders;

import io.github.reconsolidated.malinka.basket.BasketLoyaltyProduct;
import io.github.reconsolidated.malinka.basket.BasketProduct;
import io.github.reconsolidated.malinka.delivery.Delivery;
import io.github.reconsolidated.malinka.delivery.DeliveryMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * class for order
 */
@Getter
@Setter
@Entity(name = "orders")
public class Order {
    private static long lastId = 0;
    @Id
    @GeneratedValue
    private Long idZamowienia;
    @Column(name="DataDostarczania")
    private String pickupTime;
    @Column(name="DataPoczatkowa")
    private LocalDateTime date;
    @Column(name="PunktyLojalnosciowe")
    private Integer loyaltyPoints;
    @Column(name="Status")
    private boolean paymentSuccessful;
    @Column(name="IdKoszyka")
    private Integer basketId;
    @Column(name="IdPracownika")
    private Integer employeeId;
    @Column(name="IdDostawy")
    private Integer deliveryId;
    @Column(name="IdPlatnosci")
    private Integer paymentId;
    @Column(name="IdRaportu")
    private Integer reportId;
    private String address;
    private String city;
    private String street;
    private String localNumber;
    private String selectParcelLocker;
    @Transient
    private String cost;
    @Transient
    private int amount;
    @Transient
    private List<BasketProduct> products = new ArrayList<>();
    @Transient
    private List<BasketLoyaltyProduct> loyaltyProducts = new ArrayList<>();
    @Transient
    private Delivery delivery;
    @Transient
    private DeliveryMethod deliveryMethod;
    @Transient
    private String paymentMethod;
    private long id;

    public Order() {
        id = lastId++;
    }
}
