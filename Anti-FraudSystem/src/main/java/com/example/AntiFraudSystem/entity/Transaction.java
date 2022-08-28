package com.example.AntiFraudSystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "transaction_table")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

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

    @NotNull
//    @Pattern(regexp = "^20[0-9]{2}-(0[1-9]|1[0-2])-([0-2][1-9]|3[0-1])T([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$")
    private LocalDateTime date;

    private String result;

    private String feedback;
}
