package com.example.AntiFraudSystem.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackRequest {
    private Long transactionId;
    private String feedback;
}
