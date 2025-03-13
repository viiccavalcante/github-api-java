package com.example.githubapi.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tracked_repository")
public class TrackedRepo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "repo_id", nullable = false)
    private RepoInformation repo;

    @Column(nullable = false)
    private String email;

    @ElementCollection(targetClass = AlertsType.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "repository_tracking_alerts", joinColumns = @JoinColumn(name = "alert_id"))
    @Column(name = "event")
    private List<AlertsType> alertsFor;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RepoInformation getRepo() {
        return repo;
    }

    public void setRepo(RepoInformation repo) {
        this.repo = repo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AlertsType> getAlertsFor() {
        return alertsFor;
    }

    public void setAlertsFor(List<AlertsType> alertsFor) {
        this.alertsFor = alertsFor;
    }

    public enum AlertsType {
        COMMIT,
        PULL_REQUEST,
        ISSUE,
        RELEASE
    }
}
