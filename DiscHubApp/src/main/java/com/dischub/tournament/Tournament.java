package com.dischub.tournament;

import java.util.ArrayList;

/**
 *
 * @author Rowan
 */
public class Tournament {

    private final int amountOfTeams;
    private ArrayList<Team> teams;

    /**
     *
     * @param amountOfTeams
     */
    public Tournament(int amountOfTeams) {
        this.amountOfTeams = amountOfTeams;
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
}
