package com.example.githubapi.services;

import org.springframework.stereotype.Service;
import com.example.githubapi.models.RepoInformation;
import com.example.githubapi.repositories.RepoInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class RepoStatisticsService {
    private final RepoInformationRepository repoInformationRepository;
    private final GithubService githubService;

    @Autowired
    public RepoStatisticsService(RepoInformationRepository repoInformationRepository, GithubService githubService) {
        this.repoInformationRepository = repoInformationRepository;
        this.githubService = githubService;
    }

    @Scheduled(fixedRate = 30000)
    public void updateRepositoryInformation() {
        Iterable<RepoInformation> repositories = repoInformationRepository.findAll();

        for (RepoInformation repoInfo : repositories) {
            int commits = githubService.getStatsFromRepo( repoInfo.getOwner(), repoInfo.getName(), "commits");
            int pulls = githubService.getStatsFromRepo( repoInfo.getOwner(), repoInfo.getName(), "pulls");
            int issues = githubService.getStatsFromRepo( repoInfo.getOwner(), repoInfo.getName(), "issues");
            int releases = githubService.getStatsFromRepo( repoInfo.getOwner(), repoInfo.getName(), "releases");


            repoInfo.setTotalCommits(commits);
            repoInfo.setTotalPullRequests(pulls);
            repoInfo.setTotalIssues(issues);
            repoInfo.setTotalReleases(releases);

            repoInformationRepository.save(repoInfo);
        }
    }
}
