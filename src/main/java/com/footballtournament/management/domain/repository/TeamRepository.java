package com.footballtournament.management.domain.repository;

import com.footballtournament.management.domain.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository {

    List<Team> findAll();

    Optional<Team> findById(Integer id);

    List<Team> findByTournamentId(Integer tournamentId);

    Optional<Team> save(
            Integer tournamentId,
            Team team
    );

    boolean existsById(Integer id);

    void deleteById(Integer id);
}
