package com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;


@Entity
@Data
public class Address {

    @Id
    private Long user_id;

    private String address;

    private String city;

    private String state;

    private String country;

    @OneToOne
    @MapsId
    private User user;

}
