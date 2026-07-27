package com.footballtournament.management.persistence.repository;

import com.footballtournament.management.persistence.entity.TournamentMatchEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentMatchRepository
        extends CrudRepository<TournamentMatchEntity, Integer> {

    List<TournamentMatchEntity> findByTournament_Id(Integer tournamentId);

    List<TournamentMatchEntity>
    findByHomeTeamIgnoreCaseOrAwayTeamIgnoreCase(
            String homeTeam,
            String awayTeam
    );
}

