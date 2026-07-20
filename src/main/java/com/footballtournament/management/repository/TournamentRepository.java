package com.footballtournament.management.repository;

import com.footballtournament.management.entity.Tournament;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository
        extends CrudRepository<Tournament, Integer> {
}
