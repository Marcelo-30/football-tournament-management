package com.footballtournament.management.persistence.crud;

import com.footballtournament.management.persistence.entity.TournamentMatchEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentMatchCrudRepository
        extends CrudRepository<TournamentMatchEntity, Integer> {

    List<TournamentMatchEntity> findByTournament_Id(Integer tournamentId);

    List<TournamentMatchEntity>
    findByHomeTeam_NameIgnoreCaseOrAwayTeam_NameIgnoreCase(
            String homeTeam,
            String awayTeam
    );
}

