package com.footballtournament.management.web.controller;

import com.footballtournament.management.domain.model.Tournament;
import com.footballtournament.management.service.TournamentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tournaments")
@Tag(
        name = "Tournaments",
        description = "Operations for football tournaments"
)
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(
            TournamentService tournamentService
    ) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    @Operation(
            summary = "Get all tournaments",
            description = "Returns all registered football tournaments"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Tournaments retrieved successfully"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    public ResponseEntity<List<Tournament>> findAll() {
        return ResponseEntity.ok(tournamentService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get tournament by ID",
            description = "Returns a tournament when the given ID exists"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Tournament found"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Tournament not found"
    )
    public ResponseEntity<Tournament> findById(
            @Parameter(
                    description = "Tournament ID",
                    example = "1",
                    required = true
            )
            @PathVariable Integer id
    ) {
        return tournamentService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/season/{season}")
    @Operation(
            summary = "Get tournaments by season",
            description = "Returns tournaments belonging to the specified season"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Tournaments found"
    )
    @ApiResponse(
            responseCode = "404",
            description = "No tournaments found for the specified season"
    )
    public ResponseEntity<List<Tournament>> findBySeason(
            @Parameter(
                    description = "Tournament season",
                    example = "2026-2027",
                    required = true
            )
            @PathVariable String season
    ) {
        List<Tournament> tournaments =
                tournamentService.findBySeason(season);

        if (tournaments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tournaments);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search tournaments by name",
            description = "Returns tournaments whose name contains the provided text"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Tournaments found"
    )
    @ApiResponse(
            responseCode = "404",
            description = "No tournaments found"
    )
    public ResponseEntity<List<Tournament>> findByName(
            @Parameter(
                    description = "Text contained in the tournament name",
                    example = "Champions",
                    required = true
            )
            @RequestParam String name
    ) {
        List<Tournament> tournaments =
                tournamentService.findByName(name);

        if (tournaments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tournaments);
    }

    @PostMapping
    @Operation(
            summary = "Create a tournament",
            description = "Registers a new football tournament",
            requestBody =
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Tournament example",
                                    value = """
                                            {
                                              "name": "Regional Football Cup",
                                              "season": "2026-2027"
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Tournament created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid Tournament data")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "409", description = "Tournament conflict (duplicate code or SKU)")
    @ApiResponse(responseCode = "500", description = "Internal server error")
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
    @Operation(
            summary = "Delete tournament by ID",
            description = "Deletes the tournament when the given ID exists"
    )
    @ApiResponse(responseCode = "200", description = "Tournament deleted successfully")
    @ApiResponse(responseCode = "400", description = "Invalid Tournament ID")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Tournament not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> deleteById(
            @Parameter(
                    description = "Tournament ID",
                    example = "1",
                    required = true
            )
            @PathVariable Integer id
    ) {
        boolean deleted = tournamentService.deleteById(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
