package com.example.hotelmanagementsystem.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
}
