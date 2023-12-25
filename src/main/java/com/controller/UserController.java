package com.controller;

import com.dtos.PasswordGenerateDto;
import com.dtos.UpdateUserDto;
import com.dtos.UserDto;
import com.entity.User;
import com.service.PasswordEncryptionService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@PathVariable Long id){
        User userById = userService.getUserById(id);
       // return new UserDto(userById);
        return new ResponseEntity(new UserDto(userById),HttpStatus.OK);

    }

    @PutMapping ("/update-user/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody UpdateUserDto updateUserDto){
      return new ResponseEntity(new UserDto(userService.UpdateUser(id,updateUserDto)),HttpStatus.CREATED)  ;
    }

    @PostMapping(value = "/create-user",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody UserDto userDto){
       return new ResponseEntity(userService.createUser(userDto),HttpStatus.OK) ;

    }
//
    //USER LIST FROM USER DTO
    @GetMapping(value = "/retrieve-user-list",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserList(){
        List<UserDto> list = userService.getUserList();
//        UserListDto userListDto=new UserListDto();
//        list.stream().forEach(e->userListDto.getUserList().add(e));
//
//        return new ResponseEntity(userListDto,HttpStatus.OK);
        return new ResponseEntity(list,HttpStatus.OK);
    }

    //USER LIST FROM DIRECT USER ENTITY
    @GetMapping(value = "/example")
    public ResponseEntity exampleMethod (){
        return new ResponseEntity(userService.retrieveAllUsers(),HttpStatus.OK);

    }

    @PostMapping("/salt")
    public ResponseEntity<String> generateSalt(@RequestBody PasswordGenerateDto passwordGenerateDto) {
        return new ResponseEntity<String>(PasswordEncryptionService.generateSalt(32,passwordGenerateDto.getPassword()), HttpStatus.CREATED);
    }
}
