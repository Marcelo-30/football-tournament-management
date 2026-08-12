package com.footballtournament.management.persistence.repository;

import com.footballtournament.management.domain.model.TournamentMatch;
import com.footballtournament.management.domain.repository.TournamentMatchRepository;
import com.footballtournament.management.persistence.crud.TournamentCrudRepository;
import com.footballtournament.management.persistence.crud.TournamentMatchCrudRepository;
import com.footballtournament.management.persistence.entity.TournamentEntity;
import com.footballtournament.management.persistence.entity.TournamentMatchEntity;
import com.footballtournament.management.persistence.mapper.TournamentMatchMapper;
import org.springframework.stereotype.Repository;
import com.footballtournament.management.persistence.crud.TeamCrudRepository;
import com.footballtournament.management.persistence.entity.TeamEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public class TournamentMatchRepositoryImpl
        implements TournamentMatchRepository {

    private final TournamentMatchCrudRepository crudRepository;
    private final TournamentMatchMapper mapper;
    private final TournamentCrudRepository tournamentCrudRepository;
    private final TeamCrudRepository teamCrudRepository;

    public TournamentMatchRepositoryImpl(
            TournamentMatchCrudRepository crudRepository,
            TournamentCrudRepository tournamentCrudRepository,
            TeamCrudRepository teamCrudRepository,
            TournamentMatchMapper mapper
    ) {
        this.crudRepository = crudRepository;
        this.tournamentCrudRepository = tournamentCrudRepository;
        this.teamCrudRepository = teamCrudRepository;
        this.mapper = mapper;
    }

    @Override
    public List<TournamentMatch> findAll() {
        return StreamSupport
                .stream(crudRepository.findAll().spliterator(), false)
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<TournamentMatch> findById(Integer id) {
        return crudRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<TournamentMatch> save(
            Integer tournamentId,
            TournamentMatch tournamentMatch
    ) {
        Optional<TournamentEntity> tournamentOptional =
                tournamentCrudRepository.findById(tournamentId);

        if (tournamentOptional.isEmpty()) {
            return Optional.empty();
        }

        Optional<TeamEntity> homeTeamOptional =
                teamCrudRepository.findByTeamIdAndTournament_Id(
                        tournamentMatch.getHomeTeamId(),
                        tournamentId
                );

        if (homeTeamOptional.isEmpty()) {
            return Optional.empty();
        }

        Optional<TeamEntity> awayTeamOptional =
                teamCrudRepository.findByTeamIdAndTournament_Id(
                        tournamentMatch.getAwayTeamId(),
                        tournamentId
                );

        if (awayTeamOptional.isEmpty()) {
            return Optional.empty();
        }

        TournamentMatchEntity entity =
                mapper.toEntity(tournamentMatch);

        entity.setTournament(tournamentOptional.get());
        entity.setHomeTeam(homeTeamOptional.get());
        entity.setAwayTeam(awayTeamOptional.get());

        TournamentMatchEntity savedEntity =
                crudRepository.save(entity);

        return Optional.of(mapper.toDomain(savedEntity));
    }

    @Override
    public boolean existsById(Integer id) {
        return crudRepository.existsById(id);
    }

    @Override
    public void deleteById(Integer id) {
        crudRepository.deleteById(id);
    }

    @Override
    public List<TournamentMatch> findByTournamentId(Integer tournamentId){
        return crudRepository.findByTournament_Id(tournamentId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<TournamentMatch> findByTeam(String team) {
        return crudRepository
                .findByHomeTeam_NameIgnoreCaseOrAwayTeam_NameIgnoreCase(
                        team,
                        team
                )
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

}
