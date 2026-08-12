package com.footballtournament.management.domain.repository;

import com.footballtournament.management.domain.model.Tournament;

import java.util.List;
import java.util.Optional;

public interface TournamentRepository {

    List<Tournament> findAll();

    Optional<Tournament> findById(Integer id);

    Tournament save(Tournament tournament);

    boolean existsById(Integer id);

    void deleteById(Integer id);

    List <Tournament> findBySeason(String season);

    List <Tournament> findByName(String name);
}