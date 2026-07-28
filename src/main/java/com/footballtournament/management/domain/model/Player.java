package com.footballtournament.management.domain.model;

public class Player {

    private Integer playerId;
    private String name;
    private Integer number;
    private String position;
    private Integer teamId;

    public Player() {
    }

    public Player(
            Integer playerId,
            String name,
            Integer number,
            String position,
            Integer teamId
    ) {
        this.playerId = playerId;
        this.name = name;
        this.number = number;
        this.position = position;
        this.teamId = teamId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }
}

