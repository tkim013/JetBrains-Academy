package com.example.AccountService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "payments_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@acme\\.com$")
    private String employee;
    @NotBlank
    @Pattern(regexp = "^(1[0-2]|0[1-9])-\\d\\d\\d\\d$", message = "Wrong date!")
    private String period;
    @NotNull
    @Min(value = 0, message = "Salary must be non negative!")
    private Long salary;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
