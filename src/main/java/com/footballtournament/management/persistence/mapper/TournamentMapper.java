package com.footballtournament.management.persistence.mapper;

import com.footballtournament.management.domain.model.Tournament;
import com.footballtournament.management.persistence.entity.TournamentEntity;
import com.footballtournament.management.persistence.entity.TournamentMatchEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        uses = TournamentMatchMapper.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TournamentMapper {

    @Mapping(source = "id", target = "tournamentId")
    Tournament toDomain(TournamentEntity entity);

    @InheritInverseConfiguration
    TournamentEntity toEntity(Tournament domain);

    @AfterMapping
    default void connectMatches(@MappingTarget TournamentEntity tournamentEntity) {
        if (tournamentEntity.getMatches() == null) {
            return;
        }

        for (TournamentMatchEntity match : tournamentEntity.getMatches()) {
            match.setTournament(tournamentEntity);
        }
    }
}
