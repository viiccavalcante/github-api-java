package com.example.github_api_java.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class TrackRepoDTO {
    @NotBlank(message = "The email is required")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "The repo owner is required")
    private String repoOwner;

    @NotBlank(message = "The repo name is required")
    private String repoName;

    private List<String> alertsFor;

    public String getEmail() {
        return email;
    }
}
