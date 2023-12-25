package com.dtos;

import lombok.Data;

import java.util.ArrayList;

@Data
public class UserListDto {

    private ArrayList<UserDto> userList;

    public UserListDto(){
        userList = new ArrayList<>();
    }
}
