package io.github.reconsolidated.malinka.delivery;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *  specific class for parcel delivery
 **/
@Setter
@Getter
@Entity
@AllArgsConstructor
public class ParcelDelivery extends Delivery{
    private String selectParcelLocker;

    public ParcelDelivery() {}
}
