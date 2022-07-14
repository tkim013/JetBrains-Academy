package com.example.AccountService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteUserResponse {

    private String user;
    private String status;
}
