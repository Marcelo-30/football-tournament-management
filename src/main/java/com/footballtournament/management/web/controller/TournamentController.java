package com.footballtournament.management.web.controller;

import com.footballtournament.management.domain.model.Tournament;
import com.footballtournament.management.service.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(
            TournamentService tournamentService
    ) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public ResponseEntity<List<Tournament>> findAll(
            @RequestParam(required = false) String season,
            @RequestParam(required = false) String name
    ) {
        if (season != null && !season.isBlank()) {
            return ResponseEntity.ok(
                    tournamentService.findBySeason(season)
            );
        }

        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(
                    tournamentService.findByName(name)
            );
        }

        return ResponseEntity.ok(tournamentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> findById(
            @PathVariable Integer id
    ) {
        return tournamentService
                .findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @PostMapping
    public ResponseEntity<Tournament> save(
            @RequestBody Tournament tournament
    ) {
        Tournament savedTournament =
                tournamentService.save(tournament);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedTournament);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Integer id
    ) {
        boolean deleted =
                tournamentService.deleteById(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
