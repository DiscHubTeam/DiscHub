package com.dischub.tournament;

/**
 *
 * @author Rowan
 */
public class Team {

    private String teamName;
    private int teamID;

    public Team(String teamName) {
        this.teamName = teamName;
    }

    /**
     * @return the teamName
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * @param teamName the teamName to set
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * @return the teamID
     */
    public int getTeamID() {
        return teamID;
    }

    /**
     * @param teamID the teamID to set
     */
    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }
}
