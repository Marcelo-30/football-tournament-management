package com.footballtournament.management.service;

import com.footballtournament.management.domain.model.Tournament;
import com.footballtournament.management.persistence.entity.TournamentEntity;
import com.footballtournament.management.persistence.mapper.TournamentMapper;
import com.footballtournament.management.persistence.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final TournamentMapper tournamentMapper;

    public TournamentService(
            TournamentRepository tournamentRepository,
            TournamentMapper tournamentMapper
    ) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentMapper = tournamentMapper;
    }

    public List<Tournament> findAll() {
        return StreamSupport
                .stream(tournamentRepository.findAll().spliterator(), false)
                .map(tournamentMapper::toDomain)
                .toList();
    }

    public Optional<Tournament> findById(Integer id) {
        return tournamentRepository
                .findById(id)
                .map(tournamentMapper::toDomain);
    }

    public List<Tournament> findBySeason(String season) {
        return tournamentRepository
                .findBySeasonIgnoreCase(season)
                .stream()
                .map(tournamentMapper::toDomain)
                .toList();
    }

    public List<Tournament> findByName(String name) {
        return tournamentRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(tournamentMapper::toDomain)
                .toList();
    }

    public Tournament save(Tournament tournament) {
        TournamentEntity entity =
                tournamentMapper.toEntity(tournament);

        TournamentEntity savedEntity =
                tournamentRepository.save(entity);

        return tournamentMapper.toDomain(savedEntity);
    }

    public boolean deleteById(Integer id) {
        if (!tournamentRepository.existsById(id)) {
            return false;
        }

        tournamentRepository.deleteById(id);
        return true;
    }
}
