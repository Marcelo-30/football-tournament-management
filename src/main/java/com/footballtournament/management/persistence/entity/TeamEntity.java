package com.footballtournament.management.persistence.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Integer teamId;

    @Column(nullable = false)
    private String name;

    private String city;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private TournamentEntity tournament;

    @OneToMany(
            mappedBy = "team",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PlayerEntity> players = new ArrayList<>();

    public TeamEntity() {
    }
    
    public TeamEntity(
            Integer teamId,
            String name,
            String city,
            TournamentEntity tournament,
            List<PlayerEntity> players
    ) {
        this.teamId = teamId;
        this.name = name;
        this.city = city;
        this.tournament = tournament;
        this.players = players;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public TournamentEntity getTournament() {
        return tournament;
    }

    public void setTournament(TournamentEntity tournament) {
        this.tournament = tournament;
    }

    public List<PlayerEntity> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerEntity> players) {
        this.players = players;

        if (players != null) {
            players.forEach(player -> player.setTeam(this));
        }
    }

    public void addPlayer(PlayerEntity player) {
        players.add(player);
        player.setTeam(this);
    }

    public void removePlayer(PlayerEntity player) {
        players.remove(player);
        player.setTeam(null);
    }
}
