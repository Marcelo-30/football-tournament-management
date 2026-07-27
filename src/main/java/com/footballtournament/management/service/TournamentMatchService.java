package com.footballtournament.management.service;

import com.footballtournament.management.domain.model.TournamentMatch;
import com.footballtournament.management.persistence.entity.TournamentEntity;
import com.footballtournament.management.persistence.entity.TournamentMatchEntity;
import com.footballtournament.management.persistence.mapper.TournamentMatchMapper;
import com.footballtournament.management.persistence.repository.TournamentMatchRepository;
import com.footballtournament.management.persistence.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class TournamentMatchService {

    private final TournamentMatchRepository matchRepository;
    private final TournamentRepository tournamentRepository;
    private final TournamentMatchMapper matchMapper;

    public TournamentMatchService(
            TournamentMatchRepository matchRepository,
            TournamentRepository tournamentRepository,
            TournamentMatchMapper matchMapper
    ) {
        this.matchRepository = matchRepository;
        this.tournamentRepository = tournamentRepository;
        this.matchMapper = matchMapper;
    }

    public List<TournamentMatch> findAll() {
        return StreamSupport
                .stream(matchRepository.findAll().spliterator(), false)
                .map(matchMapper::toDomain)
                .toList();
    }

    public Optional<TournamentMatch> findById(Integer id) {
        return matchRepository
                .findById(id)
                .map(matchMapper::toDomain);
    }

    public List<TournamentMatch> findByTournamentId(
            Integer tournamentId
    ) {
        return matchRepository
                .findByTournament_Id(tournamentId)
                .stream()
                .map(matchMapper::toDomain)
                .toList();
    }

    public List<TournamentMatch> findByTeam(String team) {
        return matchRepository
                .findByHomeTeamIgnoreCaseOrAwayTeamIgnoreCase(
                        team,
                        team
                )
                .stream()
                .map(matchMapper::toDomain)
                .toList();
    }

    public Optional<TournamentMatch> save(
            Integer tournamentId,
            TournamentMatch tournamentMatch
    ) {
        Optional<TournamentEntity> tournamentOptional =
                tournamentRepository.findById(tournamentId);

        if (tournamentOptional.isEmpty()) {
            return Optional.empty();
        }

        TournamentMatchEntity matchEntity =
                matchMapper.toEntity(tournamentMatch);

        matchEntity.setTournament(tournamentOptional.get());

        TournamentMatchEntity savedMatch =
                matchRepository.save(matchEntity);

        return Optional.of(matchMapper.toDomain(savedMatch));
    }

    public boolean deleteById(Integer id) {
        if (!matchRepository.existsById(id)) {
            return false;
        }

        matchRepository.deleteById(id);
        return true;
    }
}