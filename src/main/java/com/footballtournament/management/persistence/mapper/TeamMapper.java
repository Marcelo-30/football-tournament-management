package com.footballtournament.management.persistence.mapper;

import com.footballtournament.management.domain.model.Team;
import com.footballtournament.management.persistence.entity.PlayerEntity;
import com.footballtournament.management.persistence.entity.TeamEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = PlayerMapper.class
)
public interface TeamMapper {

    @Mapping(
            source = "tournament.id",
            target = "tournamentId"
    )
    Team toDomain(TeamEntity entity);

    List<Team> toDomainList(List<TeamEntity> entities);

    @Mapping(
            target = "tournament",
            ignore = true
    )
    TeamEntity toEntity(Team team);

    @AfterMapping
    default void linkPlayers(
            @MappingTarget TeamEntity teamEntity
    ) {
        if (teamEntity.getPlayers() == null) {
            return;
        }

        for (PlayerEntity player : teamEntity.getPlayers()) {
            player.setTeam(teamEntity);
        }
    }
}
