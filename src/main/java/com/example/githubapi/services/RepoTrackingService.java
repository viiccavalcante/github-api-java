package com.example.githubapi.services;

import com.example.githubapi.dtos.RepoGithubResponseDto;
import com.example.githubapi.dtos.RepoTrackingRequestDto;
import com.example.githubapi.mappers.DtoToRepoInformationMapper;
import com.example.githubapi.models.RepoInformation;
import com.example.githubapi.models.TrackedRepo;
import com.example.githubapi.repositories.RepoInformationRepository;
import com.example.githubapi.repositories.TrackedRepoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RepoTrackingService {

    private final TrackedRepoRepository repoTrackingRepository;
    private final RepoInformationRepository repoInformationRepository;
    private final GithubService githubService;
    private final DtoToRepoInformationMapper dtoToRepoInformationMapper;

    @Autowired
    public RepoTrackingService(TrackedRepoRepository repoTrackingRepository, RepoInformationRepository repoInformationRepository,
                               GithubService githubService, DtoToRepoInformationMapper dtoToRepoInformationMapper) {
        this.repoTrackingRepository = repoTrackingRepository;
        this.repoInformationRepository = repoInformationRepository;
        this.githubService = githubService;
        this.dtoToRepoInformationMapper = dtoToRepoInformationMapper;

    }

    public TrackedRepo trackRepository(RepoTrackingRequestDto repoTrackingRequestDTO) {

        RepoGithubResponseDto repoGithubResponseDto = githubService.getRepoIfExists(
                repoTrackingRequestDTO.getRepoOwner(),
                repoTrackingRequestDTO.getRepoName()
        );

        if (repoGithubResponseDto == null) {
            throw new RuntimeException("Repo not found on Github.");
        }

        RepoInformation repoInformation = dtoToRepoInformationMapper.toEntity(repoGithubResponseDto);

        repoInformation = repoInformationRepository.saveIfDoesNotExist(repoInformation);

        TrackedRepo trackedRepo = new TrackedRepo();
        trackedRepo.setEmail(repoTrackingRequestDTO.getEmail());
        trackedRepo.setRepo(repoInformation);
        trackedRepo.setAlertsFor(repoTrackingRequestDTO.getAlertsFor());

        repoTrackingRepository.save(trackedRepo);

        return trackedRepo;
    }

    public Optional<RepoInformation> getRepositoryById(Long repoId) {
        return repoInformationRepository.findById(repoId);
    }

    public List<RepoInformation> getAllTrackedByEmail(String email) {
        List<RepoInformation> trackedRepos = repoTrackingRepository.findByEmail(email)
                .stream()
                .map(TrackedRepo::getRepo)
                .collect(Collectors.toList());

        return trackedRepos;
    }

    public boolean deleteTrackedRepository(Long trackedRepoId) {
        if (repoTrackingRepository.existsById(trackedRepoId)) {
            TrackedRepo tracked = repoTrackingRepository.findById(trackedRepoId)
                    .orElseThrow(() -> new RuntimeException("Repository not found"));

            Long repoId = tracked.getRepo().getId();

            if(repoTrackingRepository.countByRepoId(repoId) == 1){
                repoInformationRepository.deleteById(repoId);
            }

            repoTrackingRepository.deleteById(trackedRepoId);
            return true;
        }
        return false;
    }
}
