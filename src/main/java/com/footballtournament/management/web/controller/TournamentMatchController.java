package com.footballtournament.management.web.controller;

import com.footballtournament.management.domain.model.TournamentMatch;
import com.footballtournament.management.service.TournamentMatchService;
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
@RequestMapping("/api/matches")
public class TournamentMatchController {

    private final TournamentMatchService matchService;

    public TournamentMatchController(
            TournamentMatchService matchService
    ) {
        this.matchService = matchService;
    }

    @GetMapping
    public ResponseEntity<List<TournamentMatch>> findAll(
            @RequestParam(required = false) String team
    ) {
        if (team != null && !team.isBlank()) {
            return ResponseEntity.ok(
                    matchService.findByTeam(team)
            );
        }

        return ResponseEntity.ok(matchService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentMatch> findById(
            @PathVariable Integer id
    ) {
        return matchService
                .findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @GetMapping("/tournament/{tournamentId}")
    public ResponseEntity<List<TournamentMatch>>
    findByTournamentId(
            @PathVariable Integer tournamentId
    ) {
        return ResponseEntity.ok(
                matchService.findByTournamentId(tournamentId)
        );
    }

    @PostMapping("/tournament/{tournamentId}")
    public ResponseEntity<TournamentMatch> save(
            @PathVariable Integer tournamentId,
            @RequestBody TournamentMatch tournamentMatch
    ) {
        return matchService
                .save(tournamentId, tournamentMatch)
                .map(savedMatch ->
                        ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(savedMatch)
                )
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Integer id
    ) {
        boolean deleted =
                matchService.deleteById(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
