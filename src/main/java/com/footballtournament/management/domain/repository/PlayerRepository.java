package com.footballtournament.management.domain.repository;

import com.footballtournament.management.domain.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {

    List<Player> findAll();

    Optional<Player> findById(Integer id);

    List<Player> findByTeamId(Integer teamId);

    Optional<Player> save(
            Integer teamId,
            Player player
    );

    boolean existsById(Integer id);

    void deleteById(Integer id);
}
