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

    public TrackedRepo createRepository(RepoTrackingRequestDto repoTrackingRequestDTO) {

        RepoGithubResponseDto repoGithubResponseDto = githubService.getRepoIfExists(
                repoTrackingRequestDTO.getRepoOwner(),
                repoTrackingRequestDTO.getRepoName()
        );

        if (repoGithubResponseDto == null) {
            throw new RuntimeException("Repo not found on Github.");
        }

        RepoInformation repoInformation = dtoToRepoInformationMapper.toEntity(repoGithubResponseDto);
        repoInformationRepository.save(repoInformation);

        TrackedRepo trackedRepo = new TrackedRepo();
        trackedRepo.setEmail(repoTrackingRequestDTO.getEmail());
        trackedRepo.setRepo(repoInformation);
        trackedRepo.setAlertsFor(repoTrackingRequestDTO.getAlertsFor());

        repoTrackingRepository.save(trackedRepo);

        return trackedRepo;
    }

    public boolean deleteTrackedRepository(Long trackedRepoId) {
        if (repoTrackingRepository.existsById(trackedRepoId)) {
            TrackedRepo tracked = repoTrackingRepository.findById(trackedRepoId)
                    .orElseThrow(() -> new RuntimeException("Repository not found"));

            Long repoId = tracked.getRepo().getId();

            repoTrackingRepository.deleteById(trackedRepoId);
            repoInformationRepository.deleteById(repoId);

            return true;
        }
        return false;
    }
}
