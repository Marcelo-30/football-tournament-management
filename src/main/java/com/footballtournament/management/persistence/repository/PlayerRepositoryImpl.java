package com.footballtournament.management.persistence.repository;

import com.footballtournament.management.domain.model.Player;
import com.footballtournament.management.domain.repository.PlayerRepository;
import com.footballtournament.management.persistence.crud.PlayerCrudRepository;
import com.footballtournament.management.persistence.crud.TeamCrudRepository;
import com.footballtournament.management.persistence.entity.PlayerEntity;
import com.footballtournament.management.persistence.entity.TeamEntity;
import com.footballtournament.management.persistence.mapper.PlayerMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public class PlayerRepositoryImpl implements PlayerRepository {

    private final PlayerCrudRepository crudRepository;
    private final TeamCrudRepository teamCrudRepository;
    private final PlayerMapper mapper;

    public PlayerRepositoryImpl(
            PlayerCrudRepository crudRepository,
            TeamCrudRepository teamCrudRepository,
            PlayerMapper mapper
    ) {
        this.crudRepository = crudRepository;
        this.teamCrudRepository = teamCrudRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Player> findAll() {
        return StreamSupport
                .stream(
                        crudRepository.findAll().spliterator(),
                        false
                )
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Player> findById(Integer id) {
        return crudRepository
                .findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Player> findByTeamId(Integer teamId) {
        return crudRepository
                .findByTeam_TeamId(teamId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public Optional<Player> save(
            Integer teamId,
            Player player
    ) {
        Optional<TeamEntity> teamOptional =
                teamCrudRepository.findById(teamId);

        if (teamOptional.isEmpty()) {
            return Optional.empty();
        }

        PlayerEntity entity = mapper.toEntity(player);

        entity.setTeam(teamOptional.get());

        PlayerEntity savedEntity =
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
