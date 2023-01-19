package io.github.reconsolidated.malinka.delivery;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *  specific class for standard delivery
 **/
@Getter
@Setter
@Entity
@AllArgsConstructor
public class StandardDelivery extends Delivery{
    private String address;
    private String city;
    private String street;
    private String localNumber;

    public StandardDelivery() {}
}
