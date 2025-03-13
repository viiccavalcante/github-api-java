package com.example.githubapi.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "repository_information")
public class RepoInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String owner;

    @Column
    private String description;

    @Column(nullable = false)
    private String url;

/*    @Column
    private int totalCommits;

    @Column
    private int openPullRequests;

    @Column
    private int openIssues;

    @Column
    private int totalReleases;*/

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

    public RepoInformation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

/*    public int getTotalCommits() {
        return totalCommits;
    }

    public void setTotalCommits(int totalCommits) {
        this.totalCommits = totalCommits;
    }

    public int getOpenPullRequests() {
        return openPullRequests;
    }

    public void setOpenPullRequests(int openPullRequests) {
        this.openPullRequests = openPullRequests;
    }

    public int getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(int openIssues) {
        this.openIssues = openIssues;
    }

    public int getTotalReleases() {
        return totalReleases;
    }*/

/*    public void setTotalReleases(int totalReleases) {
        this.totalReleases = totalReleases;
    }*/
}
