package com.footballtournament.management.persistence.repository;

import com.footballtournament.management.domain.model.Tournament;
import com.footballtournament.management.domain.repository.TournamentRepository;
import com.footballtournament.management.persistence.crud.TournamentCrudRepository;
import com.footballtournament.management.persistence.entity.TournamentEntity;
import com.footballtournament.management.persistence.mapper.TournamentMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public class TournamentRepositoryImpl implements TournamentRepository {

    private final TournamentCrudRepository crudRepository;
    private final TournamentMapper mapper;

    public TournamentRepositoryImpl(
            TournamentCrudRepository crudRepository,
            TournamentMapper mapper
    ) {
        this.crudRepository = crudRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Tournament> findAll() {
        return StreamSupport
                .stream(crudRepository.findAll().spliterator(), false)
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Tournament> findById(Integer id) {
        return crudRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Tournament save(Tournament tournament) {
        TournamentEntity entity = mapper.toEntity(tournament);
        TournamentEntity savedEntity = crudRepository.save(entity);

        return mapper.toDomain(savedEntity);
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
    public List<Tournament> findBySeason(String season){
        return crudRepository.findBySeasonIgnoreCase(season).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Tournament> findByName(String name){
        return crudRepository.findByNameContainingIgnoreCase(name).stream()
                .map(mapper::toDomain)
                .toList();
    }
}