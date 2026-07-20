package com.footballtournament.management.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "season", nullable = false, length = 20)
    private String season;

    @OneToMany(
            mappedBy = "tournament",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TournamentMatch> matches = new ArrayList<>();

    public Tournament() {
    }

    public Tournament(String name, String season) {
        this.name = name;
        this.season = season;
    }

    public void addMatch(TournamentMatch match) {
        matches.add(match);
        match.setTournament(this);
    }

    public void removeMatch(TournamentMatch match) {
        matches.remove(match);
        match.setTournament(null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
