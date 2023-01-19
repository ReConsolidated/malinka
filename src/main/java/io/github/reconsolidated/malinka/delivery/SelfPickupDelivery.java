package io.github.reconsolidated.malinka.delivery;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *  specific class for self pickup
 **/
@Getter
@Setter
@Entity
@AllArgsConstructor
public class SelfPickupDelivery extends Delivery{
    private String pickupTime;

    public SelfPickupDelivery() {}
}
