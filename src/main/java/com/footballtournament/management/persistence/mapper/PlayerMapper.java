package com.footballtournament.management.persistence.mapper;

import com.footballtournament.management.domain.model.Player;
import com.footballtournament.management.persistence.entity.PlayerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(source = "playerId", target = "playerId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "number", target = "number")
    @Mapping(source = "position", target = "position")
    @Mapping(source = "team.teamId", target = "teamId")
    Player toDomain(PlayerEntity entity);

    List<Player> toDomainList(List<PlayerEntity> entities);

    @Mapping(source = "playerId", target = "playerId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "number", target = "number")
    @Mapping(source = "position", target = "position")
    @Mapping(target = "team", ignore = true)
    PlayerEntity toEntity(Player player);
}
