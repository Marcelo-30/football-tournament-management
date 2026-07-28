package com.footballtournament.management.persistence.crud;

import com.footballtournament.management.persistence.entity.TeamEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamCrudRepository
        extends CrudRepository<TeamEntity, Integer> {

    List<TeamEntity> findByTournament_Id(
            Integer tournamentId
    );
}
