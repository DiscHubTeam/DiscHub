package com.dischub.tournament;

import java.util.ArrayList;

/**
 *
 * @author Rowan
 */
public class Tournament {

    private final int amountOfTeams;
    private ArrayList<Team> teams;
    private int defaultMatchDurationSecs;

    /**
     *
     * @param amountOfTeams
     */
    public Tournament(int amountOfTeams) {
        this.amountOfTeams = amountOfTeams;
    }
    
    /**
     *
     * @param amountOfTeams
     * @param matchDurationSecs
     */
    public Tournament(int amountOfTeams, int matchDurationSecs) {
        this.amountOfTeams = amountOfTeams;
        this.defaultMatchDurationSecs = matchDurationSecs;
    }

    /**
     *
     * @param team
     */
    public void addTeam(Team team) {
        if (teams.size() <= amountOfTeams) {
            teams.add(team);
        }
    }

    /**
     *
     * @param team
     */
    public void removeTeam(Team team) {
        teams.remove(team);
    }

    /**
     *
     * @return
     */
    public ArrayList<Team> getTournamentTeamList() {
        return teams;
    }

    /**
     * @return the defaultMatchDurationSecs
     */
    public int getDefaultMatchDurationSecs() {
        return defaultMatchDurationSecs;
    }

    /**
     * @param defaultMatchDurationSecs the defaultMatchDurationSecs to set
     */
    public void setDefaultMatchDurationSecs(int defaultMatchDurationSecs) {
        this.defaultMatchDurationSecs = defaultMatchDurationSecs;
    }
}
