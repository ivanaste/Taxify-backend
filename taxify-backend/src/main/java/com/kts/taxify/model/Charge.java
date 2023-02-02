package com.kts.taxify.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "charge")
public class Charge extends BaseEntity {
    @Column(name = "amount")
    Double amount;

    @Column(name = "payment_method_id")
    String paymentMethodId;

    @Column(name = "customer_id")
    String customerId;

    @Column(name = "payment_id")
    String paymentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ride_id")
    Ride ride;

    String currency = "EUR";

    @Column(name = "charged")
    Boolean charged = false;
}
