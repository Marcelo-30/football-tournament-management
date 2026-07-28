package com.footballtournament.management.service;

import com.footballtournament.management.domain.model.Tournament;
import com.footballtournament.management.domain.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public List<Tournament> findAll() {
        return tournamentRepository.findAll();
    }

    public Optional<Tournament> findById(Integer id) {
        return tournamentRepository.findById(id);
    }

    public List<Tournament> findBySeason(String season) {
        return tournamentRepository.findBySeason(season);
    }

    public List<Tournament> findByName(String name) {
        return tournamentRepository.findByName(name);
    }

    public Tournament save(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    public boolean deleteById(Integer id) {
        if (!tournamentRepository.existsById(id)) {
            return false;
        }

        tournamentRepository.deleteById(id);
        return true;
    }
}
