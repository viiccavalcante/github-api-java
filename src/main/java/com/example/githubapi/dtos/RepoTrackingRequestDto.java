package com.example.githubapi.dtos;

import com.example.githubapi.models.TrackedRepo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class RepoTrackingRequestDto {
    @NotBlank(message = "The email is required")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "The repo owner is required")
    private String repoOwner;

    @NotBlank(message = "The repo name is required")
    private String repoName;

    private List<TrackedRepo.AlertsType> alertsFor;

    public String getEmail() {
        return email;
    }

    public String getRepoOwner() {
        return repoOwner;
    }

    public String getRepoName() {
        return repoName;
    }

    public List<TrackedRepo.AlertsType> getAlertsFor() {
        return alertsFor;
    }
}
