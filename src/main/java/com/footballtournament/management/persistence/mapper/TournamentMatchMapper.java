package com.footballtournament.management.persistence.mapper;

import com.footballtournament.management.domain.model.TournamentMatch;
import com.footballtournament.management.persistence.entity.TournamentMatchEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TournamentMatchMapper {

    @Mapping(source = "id", target = "matchId")
    TournamentMatch toDomain(TournamentMatchEntity entity);

    @InheritInverseConfiguration
    @Mapping(target = "tournament", ignore = true)
    TournamentMatchEntity toEntity(TournamentMatch domain);
}
