package com.footballtournament.management.persistence.repository;

import com.footballtournament.management.domain.model.Team;
import com.footballtournament.management.domain.repository.TeamRepository;
import com.footballtournament.management.persistence.crud.TeamCrudRepository;
import com.footballtournament.management.persistence.crud.TournamentCrudRepository;
import com.footballtournament.management.persistence.entity.TeamEntity;
import com.footballtournament.management.persistence.entity.TournamentEntity;
import com.footballtournament.management.persistence.mapper.TeamMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public class TeamRepositoryImpl implements TeamRepository {

    private final TeamCrudRepository crudRepository;
    private final TournamentCrudRepository tournamentCrudRepository;
    private final TeamMapper mapper;

    public TeamRepositoryImpl(
            TeamCrudRepository crudRepository,
            TournamentCrudRepository tournamentCrudRepository,
            TeamMapper mapper
    ) {
        this.crudRepository = crudRepository;
        this.tournamentCrudRepository = tournamentCrudRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Team> findAll() {
        return StreamSupport
                .stream(
                        crudRepository.findAll().spliterator(),
                        false
                )
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Team> findById(Integer id) {
        return crudRepository
                .findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Team> findByTournamentId(
            Integer tournamentId
    ) {
        return crudRepository
                .findByTournament_Id(tournamentId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Team> save(
            Integer tournamentId,
            Team team
    ) {
        Optional<TournamentEntity> tournamentOptional =
                tournamentCrudRepository.findById(tournamentId);

        if (tournamentOptional.isEmpty()) {
            return Optional.empty();
        }

        TeamEntity entity = mapper.toEntity(team);

        entity.setTournament(tournamentOptional.get());

        if (entity.getPlayers() != null) {
            entity.getPlayers().forEach(
                    player -> player.setTeam(entity)
            );
        }

        TeamEntity savedEntity =
                crudRepository.save(entity);

        return Optional.of(
                mapper.toDomain(savedEntity)
        );
    }

    @Override
    public boolean existsById(Integer id) {
        return crudRepository.existsById(id);
    }

    @Override
    public void deleteById(Integer id) {
        crudRepository.deleteById(id);
    }
}
