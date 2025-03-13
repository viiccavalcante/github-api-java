package com.example.githubapi.repositories;

import com.example.githubapi.models.TrackedRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrackedRepoRepository extends JpaRepository<TrackedRepo, Long> {
    @Query("SELECT COUNT(t) FROM TrackedRepo t WHERE t.repo.id = :repoId")
    long countByRepoId(@Param("repoId") Long repoId);

    List<TrackedRepo> findByEmail(String email);
}
