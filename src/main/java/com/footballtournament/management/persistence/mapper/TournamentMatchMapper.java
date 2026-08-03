package com.footballtournament.management.persistence.mapper;

import com.footballtournament.management.domain.model.TournamentMatch;
import com.footballtournament.management.persistence.entity.TournamentMatchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TournamentMatchMapper {

    @Mapping(source = "id", target = "matchId")
    @Mapping(source = "homeTeam.teamId", target = "homeTeamId")
    @Mapping(source = "homeTeam.name", target = "homeTeamName")
    @Mapping(source = "awayTeam.teamId", target = "awayTeamId")
    @Mapping(source = "awayTeam.name", target = "awayTeamName")
    TournamentMatch toDomain(TournamentMatchEntity entity);

    @Mapping(source = "matchId", target = "id")
    @Mapping(target = "homeTeam", ignore = true)
    @Mapping(target = "awayTeam", ignore = true)
    @Mapping(target = "tournament", ignore = true)
    TournamentMatchEntity toEntity(TournamentMatch domain);
}
