package com.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user5")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Contact contact;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Address address;


}
