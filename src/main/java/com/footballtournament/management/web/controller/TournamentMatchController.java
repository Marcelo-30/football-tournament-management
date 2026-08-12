package com.footballtournament.management.web.controller;

import static com.footballtournament.management.web.response.ResponseHeader.MESSAGE;
import com.footballtournament.management.domain.model.TournamentMatch;
import com.footballtournament.management.service.TournamentMatchService;
import io.swagger.v3.oas.annotations.Operation;
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
            @RequestParam(required = false) String team
    ) {
        if (team != null && !team.isBlank()) {
            List<TournamentMatch> matches =
                    matchService.findByTeam(team);

            if (matches.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .header(
                                MESSAGE,
                                "No tournament matches were found for team: "
                                        + team
                        )
                        .build();
            }

            return ResponseEntity
                    .ok()
                    .header(
                            MESSAGE,
                            "Tournament matches retrieved successfully"
                    )
                    .body(matches);
        }

        List<TournamentMatch> matches =
                matchService.findAll();

        return ResponseEntity
                .ok()
                .header(
                        MESSAGE,
                        "Tournament matches retrieved successfully"
                )
                .body(matches);
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
            @PathVariable Integer id
    ) {
        return matchService.findById(id)
                .map(match ->
                        ResponseEntity
                                .ok()
                                .header(
                                        MESSAGE,
                                        "Tournament match retrieved successfully"
                                )
                                .body(match)
                )
                .orElseGet(() ->
                        ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .header(
                                        MESSAGE,
                                        "Tournament match with ID " + id
                                                + " was not found"
                                )
                                .build()
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
            @PathVariable Integer tournamentId
    ) {
        List<TournamentMatch> matches =
                matchService.findByTournamentId(tournamentId);

        if (matches.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(
                            MESSAGE,
                            "No matches were found for tournament with ID "
                                    + tournamentId
                    )
                    .build();
        }

        return ResponseEntity
                .ok()
                .header(
                        MESSAGE,
                        "Tournament matches retrieved successfully"
                )
                .body(matches);
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
                                              "homeTeamId": 1,
                                              "awayTeamId": 2,
                                              "scheduledAt": "2026-08-03T18:00:00"
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
            description = "Tournament or one of the teams was not found"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )

    public ResponseEntity<TournamentMatch> save(
            @PathVariable Integer tournamentId,
            @RequestBody TournamentMatch tournamentMatch
    ) {
        return matchService.save(tournamentId, tournamentMatch)
                .map(savedMatch ->
                        ResponseEntity
                                .status(HttpStatus.CREATED)
                                .header(
                                        MESSAGE,
                                        "Tournament match created successfully"
                                )
                                .body(savedMatch)
                )
                .orElseGet(() ->
                        ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .header(
                                        MESSAGE,
                                        "Tournament or one of the teams was not found"
                                )
                                .build()
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
            @PathVariable Integer id
    ) {
        boolean deleted = matchService.deleteById(id);

        if (!deleted) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(
                            MESSAGE,
                            "Tournament match with ID " + id
                                    + " was not found"
                    )
                    .build();
        }

        return ResponseEntity
                .noContent()
                .header(
                        MESSAGE,
                        "Tournament match deleted successfully"
                )
                .build();
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(
            IllegalArgumentException exception
    ) {
        return ResponseEntity
                .badRequest()
                .header(
                        MESSAGE,
                        exception.getMessage()
                )
                .body(exception.getMessage());
    }
}
