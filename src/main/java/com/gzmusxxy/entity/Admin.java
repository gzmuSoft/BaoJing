package com.gzmusxxy.entity;

import lombok.Data;

@Data
public class Admin {
    private Integer id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Integer role;

    private String bank;

    private String cardNumber;

    private String payee;
}
