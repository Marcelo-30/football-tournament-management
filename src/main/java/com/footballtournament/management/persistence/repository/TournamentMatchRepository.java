package com.footballtournament.management.persistence.repository;

import com.footballtournament.management.persistence.entity.TournamentMatchEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentMatchRepository
        extends CrudRepository<TournamentMatchEntity, Integer> {
}

