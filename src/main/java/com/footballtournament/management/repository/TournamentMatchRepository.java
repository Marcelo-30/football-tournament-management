package com.footballtournament.management.repository;

import com.footballtournament.management.entity.TournamentMatch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentMatchRepository
        extends CrudRepository<TournamentMatch, Integer> {
}
