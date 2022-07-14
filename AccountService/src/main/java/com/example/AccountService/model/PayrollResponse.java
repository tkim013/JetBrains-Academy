package com.example.AccountService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayrollResponse {

    private String name;
    private String lastname;
    private String period;
    private String salary;
}
