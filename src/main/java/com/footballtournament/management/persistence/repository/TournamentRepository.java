package com.footballtournament.management.persistence.repository;

import com.footballtournament.management.persistence.entity.TournamentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository
        extends CrudRepository<TournamentEntity, Integer> {

    List<TournamentEntity> findBySeasonIgnoreCase(String season);

    List<TournamentEntity> findByNameContainingIgnoreCase(String name);
}
