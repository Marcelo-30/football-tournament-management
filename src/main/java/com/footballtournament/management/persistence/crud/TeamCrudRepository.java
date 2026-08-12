package com.footballtournament.management.persistence.crud;

import com.footballtournament.management.persistence.entity.TeamEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TeamCrudRepository
        extends CrudRepository<TeamEntity, Integer> {

    List<TeamEntity> findByTournament_Id(
            Integer tournamentId
    );

    Optional<TeamEntity> findByTeamIdAndTournament_Id(
            Integer teamId,
            Integer tournamentId
    );
}
