package com.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {


    private String username;
    private String password;

    private String firstName;
    private String lastName;

    private String email;
    private String gender;

    //contact

    private String phoneNumber;

    //address
    private String address;

    private String city;

    private String state;

    private String Country;

}
