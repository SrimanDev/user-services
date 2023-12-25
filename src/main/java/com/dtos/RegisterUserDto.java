package com.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;

}
