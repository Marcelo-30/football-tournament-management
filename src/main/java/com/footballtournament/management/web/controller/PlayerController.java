package com.footballtournament.management.web.controller;

import com.footballtournament.management.domain.model.Player;
import com.footballtournament.management.service.PlayerService;
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
@RequestMapping("/api/players")
@Tag(
        name = "Players",
        description = "Operations for football players"
)
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(
            PlayerService playerService
    ) {
        this.playerService = playerService;
    }

    @GetMapping
    @Operation(
            summary = "Get all players",
            description = "Returns all registered football players"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Players retrieved successfully"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    public ResponseEntity<List<Player>> findAll() {
        return ResponseEntity.ok(playerService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get player by ID",
            description = "Returns a player when the specified ID exists"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Player found"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Player not found"
    )
    public ResponseEntity<Player> findById(
            @Parameter(
                    description = "Player ID",
                    example = "1",
                    required = true
            )
            @PathVariable("id") Integer id
    ) {
        return playerService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @GetMapping("/team/{teamId}")
    @Operation(
            summary = "Get players by team",
            description = "Returns all players registered in a team"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Players found"
    )
    @ApiResponse(
            responseCode = "404",
            description = "No players found for the team"
    )
    public ResponseEntity<List<Player>> findByTeamId(
            @Parameter(
                    description = "Team ID",
                    example = "1",
                    required = true
            )
            @PathVariable("teamId") Integer teamId
    ) {
        List<Player> players =
                playerService.findByTeamId(teamId);

        if (players.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(players);
    }

    @PostMapping("/team/{teamId}")
    @Operation(
            summary = "Create a player",
            description = "Creates a player and associates it with a team",
            requestBody =
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Player example",
                                    value = """
                                            {
                                              "name": "Carlos Rodríguez",
                                              "number": 8,
                                              "position": "Midfielder"
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponse(
            responseCode = "201",
            description = "Player created successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid player data"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Team not found"
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
    )
    public ResponseEntity<Player> save(
            @Parameter(
                    description = "Team ID",
                    example = "1",
                    required = true
            )
            @PathVariable("teamId") Integer teamId,

            @RequestBody Player player
    ) {
        return playerService.save(teamId, player)
                .map(savedPlayer ->
                        ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(savedPlayer)
                )
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete player by ID",
            description = "Deletes a player when the specified ID exists"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Player deleted successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Player not found"
    )
    public ResponseEntity<Void> deleteById(
            @Parameter(
                    description = "Player ID",
                    example = "1",
                    required = true
            )
            @PathVariable("id") Integer id
    ) {
        boolean deleted = playerService.deleteById(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
