package com.example.astrand.footballfixtures.entities;


public class Result {

    public Result(int goalsHomeTeam, int goalsAwayTeam) {
        this.goalsHomeTeam = goalsHomeTeam;
        this.goalsAwayTeam = goalsAwayTeam;
        goalsHomeTeamHalfTime = -1;
        goalsAwayTeamHalfTime = -1;
    }


    private int goalsHomeTeam;

    private int goalsAwayTeam;

    private int goalsHomeTeamHalfTime;

    private int goalsAwayTeamHalfTime;

    public boolean hasHalfTimeResults(){
        return goalsAwayTeamHalfTime != -1 && goalsHomeTeamHalfTime != -1;
    }

    public int getGoalsHomeTeam() {
        return goalsHomeTeam;
    }

    public int getGoalsAwayTeam() {
        return goalsAwayTeam;
    }

    public int getGoalsHomeTeamHalfTime() {
        return goalsHomeTeamHalfTime;
    }

    public int getGoalsAwayTeamHalfTime() {
        return goalsAwayTeamHalfTime;
    }

    public void setGoalsHomeTeam(int goalsHomeTeam) {
        this.goalsHomeTeam = goalsHomeTeam;
    }

    public void setGoalsAwayTeam(int goalsAwayTeam) {
        this.goalsAwayTeam = goalsAwayTeam;
    }

    public void setGoalsHomeTeamHalfTime(int goalsHomeTeamHalfTime) {
        this.goalsHomeTeamHalfTime = goalsHomeTeamHalfTime;
    }

    public void setGoalsAwayTeamHalfTime(int goalsAwayTeamHalfTime) {
        this.goalsAwayTeamHalfTime = goalsAwayTeamHalfTime;
    }
}
