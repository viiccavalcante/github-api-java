package com.example.githubapi.controllers;

import com.example.githubapi.dtos.RepoTrackingRequestDto;
import com.example.githubapi.models.TrackedRepo;
import com.example.githubapi.services.RepoTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class RepoTrackingController {

    private final RepoTrackingService repoTrackingService;

    @Autowired
    public RepoTrackingController(RepoTrackingService repoTrackingService) {
        this.repoTrackingService = repoTrackingService;
    }

    @PostMapping("/track")
    public ResponseEntity<TrackedRepo> trackRepository(@Valid @RequestBody RepoTrackingRequestDto repoTrackingRequestDTO) {
        TrackedRepo trackedRepo = repoTrackingService.createRepository(repoTrackingRequestDTO);
        return new ResponseEntity<>(trackedRepo, HttpStatus.CREATED);
    }

    @DeleteMapping("/tracked/{repoId}")
    public ResponseEntity<Void> deleteRepository(@PathVariable Long trackedRepoId) {
        boolean isDeleted = repoTrackingService.deleteTrackedRepository(trackedRepoId);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
