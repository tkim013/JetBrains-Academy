package com.example.AntiFraudSystem.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeRoleRequest {
    private String username;
    private String role;
}
