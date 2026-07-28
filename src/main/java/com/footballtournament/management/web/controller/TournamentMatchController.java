package com.footballtournament.management.web.controller;

import com.footballtournament.management.domain.model.TournamentMatch;
import com.footballtournament.management.service.TournamentMatchService;
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
@RequestMapping("/api/matches")
@Tag(
        name = "Tournament matches",
        description = "Operations for football tournament matches"
)
public class TournamentMatchController {

    private final TournamentMatchService matchService;

    public TournamentMatchController(
            TournamentMatchService matchService
    ) {
        this.matchService = matchService;
    }

    @GetMapping
    @Operation(
            summary = "Get all tournament matches",
            description = """
                    Returns all registered tournament matches. 
                    An optional team parameter can be used to search matches 
                    where the team played as the home or away team.
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Tournament matches retrieved successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "No matches found for the specified team"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    public ResponseEntity<List<TournamentMatch>> findAll(
            @Parameter(
                    description = "Optional team name used to filter matches",
                    example = "Tigres",
                    required = false
            )
            @RequestParam(required = false) String team
    ) {
        if (team != null && !team.isBlank()) {
            List<TournamentMatch> matches =
                    matchService.findByTeam(team);

            if (matches.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(matches);
        }

        return ResponseEntity.ok(matchService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get tournament match by ID",
            description = "Returns a tournament match when the specified ID exists"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Tournament match found"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Tournament match not found"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    public ResponseEntity<TournamentMatch> findById(
            @Parameter(
                    description = "Tournament match ID",
                    example = "1",
                    required = true
            )
            @PathVariable("id") Integer id
    ) {
        return matchService
                .findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @GetMapping("/tournament/{tournamentId}")
    @Operation(
            summary = "Get matches by tournament ID",
            description = """
                    Returns all matches belonging to the specified tournament
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Tournament matches found"
    )
    @ApiResponse(
            responseCode = "404",
            description = "No matches found for the specified tournament"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    public ResponseEntity<List<TournamentMatch>> findByTournamentId(
            @Parameter(
                    description = "Tournament ID",
                    example = "1",
                    required = true
            )
            @PathVariable("tournamentId") Integer tournamentId
    ) {
        List<TournamentMatch> matches =
                matchService.findByTournamentId(tournamentId);

        if (matches.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(matches);
    }

    @PostMapping("/tournament/{tournamentId}")
    @Operation(
            summary = "Create a tournament match",
            description = """
                    Registers a new match and associates it with the specified tournament
                    """,
            requestBody =
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Tournament match example",
                                    value = """
                                            {
                                              "homeTeam": "Tigres",
                                              "awayTeam": "Monterrey"
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponse(
            responseCode = "201",
            description = "Tournament match created successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid tournament match data"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Tournament not found"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    public ResponseEntity<TournamentMatch> save(
            @Parameter(
                    description = "ID of the tournament to which the match belongs",
                    example = "1",
                    required = true
            )
            @PathVariable("tournamentId") Integer tournamentId,

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
    @Operation(
            summary = "Delete tournament match by ID",
            description = """
                    Deletes a tournament match when the specified ID exists
                    """
    )
    @ApiResponse(
            responseCode = "204",
            description = "Tournament match deleted successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid tournament match ID"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Tournament match not found"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    public ResponseEntity<Void> deleteById(
            @Parameter(
                    description = "Tournament match ID",
                    example = "1",
                    required = true
            )
            @PathVariable("id") Integer id
    ) {
        boolean deleted = matchService.deleteById(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
