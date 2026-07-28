package com.footballtournament.management.service;

import com.footballtournament.management.domain.model.Team;
import com.footballtournament.management.domain.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Optional<Team> findById(Integer id) {
        return teamRepository.findById(id);
    }

    public List<Team> findByTournamentId(Integer tournamentId) {
        return teamRepository.findByTournamentId(tournamentId);
    }

    public Optional<Team> save(
            Integer tournamentId,
            Team team
    ) {
        return teamRepository.save(tournamentId, team);
    }

    public boolean deleteById(Integer id) {
        if (!teamRepository.existsById(id)) {
            return false;
        }

        teamRepository.deleteById(id);
        return true;
    }
}
