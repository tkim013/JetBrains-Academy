package com.example.AntiFraudSystem.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class TransactionRequest {

    @NotNull
    private Long amount;

    @NotBlank
    @Pattern(regexp = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")
    private String ip;

    @NotBlank
    @Pattern(regexp = "^400000[0-9]{10}$")
    private String number;

    @NotBlank
    private String region;

    @NotBlank
    @Pattern(regexp = "^20[0-9]{2}-(0[1-9]|1[0-2])-([0-2][1-9]|3[0-1])T([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$")
    private String date;
}
