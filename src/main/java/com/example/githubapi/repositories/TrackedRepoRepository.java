package com.example.githubapi.repositories;

import com.example.githubapi.models.TrackedRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackedRepoRepository extends JpaRepository<TrackedRepo, Long> {
}
