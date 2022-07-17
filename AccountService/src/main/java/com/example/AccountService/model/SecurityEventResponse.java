package com.example.AccountService.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityEventResponse {

    private String date;
    private String action;
    private String subject;
    private String object;
    private String path;
}
