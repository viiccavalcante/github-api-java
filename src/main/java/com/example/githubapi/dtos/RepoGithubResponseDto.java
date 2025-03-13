package com.example.githubapi.dtos;

public class RepoGithubResponseDto {
    private String repoName;
    private String repoOwner;
    private String description;
    private String url;

    public RepoGithubResponseDto() {
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public void setRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner;
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


}
