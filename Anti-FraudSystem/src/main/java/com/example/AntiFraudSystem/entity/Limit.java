package com.example.AntiFraudSystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "limit_table")
public class Limit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int allowed;
    private int manual;

    public Limit(int amount, int manual) {
        this.allowed = amount;
        this.manual = manual;
    }
}
