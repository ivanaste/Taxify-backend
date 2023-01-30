package com.kts.taxify.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "complaint")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Complaint extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ride_id")
    Ride ride;

    @Column(name = "complaint_reason")
    String complaintReason;


}
