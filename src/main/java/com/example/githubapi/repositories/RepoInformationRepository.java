package com.example.githubapi.repositories;

import com.example.githubapi.models.RepoInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepoInformationRepository extends JpaRepository<RepoInformation, Long> {
    Optional<RepoInformation> findByNameAndOwner(String name, String owner);

    default RepoInformation saveIfDoesNotExist(RepoInformation repoInformation) {
        return findByNameAndOwner(repoInformation.getName(), repoInformation.getOwner())
                .orElseGet(() -> saveAndFlush(repoInformation));
    }
}
