package com.example.githubapi.services;

import com.example.githubapi.dtos.RepoGithubResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class GithubService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GithubService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public RepoGithubResponseDto getRepoIfExists(String owner, String repoName) {
        String url = String.format("https://api.github.com/repos/%s/%s", owner, repoName);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Error to get repo");
            }

            JsonNode rootNode = objectMapper.readTree(response.getBody());

            RepoGithubResponseDto dto = new RepoGithubResponseDto();
            dto.setRepoName(rootNode.get("name").asText());
            dto.setRepoOwner(rootNode.get("owner").get("login").asText());
            dto.setDescription(rootNode.has("description") && !rootNode.get("description").isNull()
                    ? rootNode.get("description").asText() : "");
            dto.setUrl(rootNode.get("html_url").asText());

            return dto;
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private int getStatsFromRepo(String owner, String repoName, String type) {
        String url = String.format("https://api.github.com/repos/%s/%s/%s", owner, repoName, type);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            return rootNode.isArray() ? rootNode.size() : 0;
        } catch (HttpClientErrorException.NotFound e) {
            return 0;
        } catch (Exception e) {
            throw new RuntimeException("Error to find " + type, e);
        }
    }

}
