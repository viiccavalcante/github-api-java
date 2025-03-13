package com.example.githubapi.repositories;

import com.example.githubapi.models.RepoInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoInformationRepository extends JpaRepository<RepoInformation, Long> {
}
