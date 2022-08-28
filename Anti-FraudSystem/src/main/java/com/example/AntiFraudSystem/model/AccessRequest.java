package com.example.AntiFraudSystem.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessRequest {
    private String username;
    private String operation;
}
