package com.footballtournament.management.service;

import com.footballtournament.management.domain.model.TournamentMatch;
import com.footballtournament.management.domain.repository.TournamentMatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class TournamentMatchService {

    private final TournamentMatchRepository matchRepository;

    public TournamentMatchService(
            TournamentMatchRepository matchRepository
    ) {
        this.matchRepository = matchRepository;
    }

    public List<TournamentMatch> findAll() {
        return matchRepository.findAll();
    }

    public Optional<TournamentMatch> findById(Integer id) {
        return matchRepository.findById(id);
    }

    public List<TournamentMatch> findByTournamentId(
            Integer tournamentId
    ) {
        return matchRepository.findByTournamentId(tournamentId);
    }

    public List<TournamentMatch> findByTeam(String team) {
        return matchRepository.findByTeam(team);
    }

    public Optional<TournamentMatch> save(
            Integer tournamentId,
            TournamentMatch tournamentMatch
    ) {
        if (tournamentMatch.getHomeTeamId() == null
                || tournamentMatch.getAwayTeamId() == null) {
            throw new IllegalArgumentException(
                    "Home team and away team are required"
            );
        }

        if (Objects.equals(
                tournamentMatch.getHomeTeamId(),
                tournamentMatch.getAwayTeamId()
        )) {
            throw new IllegalArgumentException(
                    "Home team and away team must be different"
            );
        }

        if (tournamentMatch.getScheduledAt() == null) {
            tournamentMatch.setScheduledAt(LocalDateTime.now());
        }

        return matchRepository.save(
                tournamentId,
                tournamentMatch
        );
    }

    public boolean deleteById(Integer id) {
        if (!matchRepository.existsById(id)) {
            return false;
        }

        matchRepository.deleteById(id);
        return true;
    }
}