package com.footballtournament.management.domain.repository;

import com.footballtournament.management.domain.model.TournamentMatch;

import java.util.List;
import java.util.Optional;

public interface TournamentMatchRepository {

    List<TournamentMatch> findAll();

    Optional<TournamentMatch> findById(Integer id);

    List<TournamentMatch> findByTournamentId(Integer tournamentId);

    List<TournamentMatch> findByTeam(String team);

    Optional<TournamentMatch> save(
            Integer tournamentId,
            TournamentMatch tournamentMatch
    );

    boolean existsById(Integer id);

    void deleteById(Integer id);
}
