package com.controller;

import com.dtos.LoginRequestDto;
import com.dtos.UserDto;
import com.entity.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    @Autowired
    private UserService userService;

    @PostMapping(value = "/login",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto){
        User user = userService.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        return new ResponseEntity(new UserDto(user), HttpStatus.ACCEPTED);
    }

}
