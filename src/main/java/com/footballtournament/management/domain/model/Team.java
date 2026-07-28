package com.footballtournament.management.domain.model;

import java.util.List;

public class Team {

    private Integer teamId;
    private String name;
    private String city;
    private Integer tournamentId;
    private List<Player> players;

    public Team() {
    }

    public Team(
            Integer teamId,
            String name,
            String city,
            Integer tournamentId,
            List<Player> players
    ) {
        this.teamId = teamId;
        this.name = name;
        this.city = city;
        this.tournamentId = tournamentId;
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

    public Integer getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Integer tournamentId) {
        this.tournamentId = tournamentId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
