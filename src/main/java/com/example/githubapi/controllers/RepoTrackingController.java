package com.example.githubapi.controllers;

import com.example.githubapi.dtos.RepoTrackingRequestDto;
import com.example.githubapi.models.*;
import com.example.githubapi.services.RepoTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

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
        TrackedRepo trackedRepo = repoTrackingService.trackRepository(repoTrackingRequestDTO);
        return new ResponseEntity<>(trackedRepo, HttpStatus.CREATED);
    }

    @GetMapping("/tracked/{repoId}")
    public ResponseEntity<RepoInformation> getRepositoryInfoById(@PathVariable Long repoId) {
        Optional<RepoInformation> repository = repoTrackingService.getRepositoryById(repoId);
        return repository.map(
                repo -> new ResponseEntity<>(repo, HttpStatus.OK)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/tracked/{trackedRepoId}")
    public ResponseEntity<String>deleteRepository(@PathVariable Long trackedRepoId) {
        boolean isDeleted = repoTrackingService.deleteTrackedRepository(trackedRepoId);
        return isDeleted ? new ResponseEntity<>("Untracked", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all-tracked/{email}")
    public ResponseEntity<List<RepoInformation>> getAllTrackedByEmail(@PathVariable String email) {
        List<RepoInformation> trackedRepos = repoTrackingService.getAllTrackedByEmail(email);
        return ResponseEntity.ok(trackedRepos);
    }
}
