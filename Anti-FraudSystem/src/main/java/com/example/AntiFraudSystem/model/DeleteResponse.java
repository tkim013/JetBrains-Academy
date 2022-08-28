package com.example.AntiFraudSystem.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteResponse {
    private String username;
    private String status;

    public DeleteResponse(String name) {
        this.username = name;
        this.status = "Deleted successfully!";
    }
}
