package com.footballtournament.management.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Tournament {

    private Integer tournamentId;
    private String name;
    private String season;
    private List<TournamentMatch> matches;

    public Tournament() {
        this.matches = new ArrayList<>();
    }

    public Tournament(
            Integer tournamentId,
            String name,
            String season,
            List<TournamentMatch> matches
    ) {
        this.tournamentId = tournamentId;
        this.name = name;
        this.season = season;
        this.matches = matches;
    }

    public Integer getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Integer tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public List<TournamentMatch> getMatches() {
        return matches;
    }

    public void setMatches(List<TournamentMatch> matches) {
        this.matches = matches;
    }
}