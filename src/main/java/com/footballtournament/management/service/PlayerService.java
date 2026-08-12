package com.footballtournament.management.service;

import com.footballtournament.management.domain.model.Player;
import com.footballtournament.management.domain.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(
            PlayerRepository playerRepository
    ) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Optional<Player> findById(Integer id) {
        return playerRepository.findById(id);
    }

    public List<Player> findByTeamId(Integer teamId) {
        return playerRepository.findByTeamId(teamId);
    }

    public Optional<Player> save(
            Integer teamId,
            Player player
    ) {
        return playerRepository.save(teamId, player);
    }

    public boolean deleteById(Integer id) {
        if (!playerRepository.existsById(id)) {
            return false;
        }

        playerRepository.deleteById(id);
        return true;
    }
}
