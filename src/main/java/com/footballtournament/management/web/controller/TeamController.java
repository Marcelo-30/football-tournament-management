package com.footballtournament.management.web.controller;

import com.footballtournament.management.domain.model.Team;
import com.footballtournament.management.service.TeamService;
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
@RequestMapping("/api/teams")
@Tag(
        name = "Teams",
        description = "Operations for football teams"
)
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    @Operation(
            summary = "Get all teams",
            description = "Returns all registered football teams"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Teams retrieved successfully"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    public ResponseEntity<List<Team>> findAll() {
        return ResponseEntity.ok(teamService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get team by ID",
            description = "Returns a team when the specified ID exists"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Team found"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Team not found"
    )
    public ResponseEntity<Team> findById(
            @Parameter(
                    description = "Team ID",
                    example = "1",
                    required = true
            )
            @PathVariable("id") Integer id
    ) {
        return teamService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @GetMapping("/tournament/{tournamentId}")
    @Operation(
            summary = "Get teams by tournament",
            description = "Returns all teams registered in a tournament"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Teams found"
    )
    @ApiResponse(
            responseCode = "404",
            description = "No teams found for the tournament"
    )
    public ResponseEntity<List<Team>> findByTournamentId(
            @Parameter(
                    description = "Tournament ID",
                    example = "1",
                    required = true
            )
            @PathVariable("tournamentId") Integer tournamentId
    ) {
        List<Team> teams =
                teamService.findByTournamentId(tournamentId);

        if (teams.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(teams);
    }

    @PostMapping("/tournament/{tournamentId}")
    @Operation(
            summary = "Create a team",
            description = "Creates a team and associates it with a tournament",
            requestBody =
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Team example",
                                    value = """
                                            {
                                              "name": "Tigres",
                                              "city": "Monterrey"
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponse(
            responseCode = "201",
            description = "Team created successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid team data"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Tournament not found"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    public ResponseEntity<Team> save(
            @Parameter(
                    description = "Tournament ID",
                    example = "1",
                    required = true
            )
            @PathVariable("tournamentId") Integer tournamentId,

            @RequestBody Team team
    ) {
        return teamService.save(tournamentId, team)
                .map(savedTeam ->
                        ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(savedTeam)
                )
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete team by ID",
            description = "Deletes a team when the specified ID exists"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Team deleted successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Team not found"
    )
    public ResponseEntity<Void> deleteById(
            @Parameter(
                    description = "Team ID",
                    example = "1",
                    required = true
            )
            @PathVariable("id") Integer id
    ) {
        boolean deleted = teamService.deleteById(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
