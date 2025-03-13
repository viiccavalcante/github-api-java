package com.example.githubapi.mappers;

import com.example.githubapi.dtos.RepoGithubResponseDto;
import com.example.githubapi.models.RepoInformation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DtoToRepoInformationMapper {
    DtoToRepoInformationMapper INSTANCE = Mappers.getMapper(DtoToRepoInformationMapper.class);

    @Mapping(source = "repoOwner", target = "owner")
    @Mapping(source = "repoName", target = "name")
    RepoInformation toEntity(RepoGithubResponseDto dto);
}
