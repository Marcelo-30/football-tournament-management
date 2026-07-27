package com.footballtournament.management.domain.model;

import java.time.LocalDateTime;

public class TournamentMatch {

    private Integer matchId;
    private String homeTeam;
    private String awayTeam;
    private LocalDateTime scheduledAt;

    public TournamentMatch() {
    }

    public TournamentMatch(
            Integer matchId,
            String homeTeam,
            String awayTeam,
            LocalDateTime scheduledAt
    ) {
        this.matchId = matchId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.scheduledAt = scheduledAt;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }
}
