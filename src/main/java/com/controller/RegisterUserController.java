package com.controller;

import com.dtos.RegisterUserDto;
import com.dtos.UserDto;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterUserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity RegisterNewUser(@RequestBody RegisterUserDto registerUserDto){
      return new ResponseEntity(new UserDto(userService.registerUser(registerUserDto)),null, HttpStatus.CREATED);
    }

}
