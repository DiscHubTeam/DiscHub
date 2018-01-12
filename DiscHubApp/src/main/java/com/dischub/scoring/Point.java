package com.dischub.scoring;

import com.dischub.tournament.Player;
import com.dischub.tournament.Team;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author rodtr
 */
public class Point {

    private Player scorer;
    private Player assister;
    private long timeOfScoreMillis;
    private Team team;
    
    public Point(){
        
    }
    
    public Point(Player scorer, Player assister,Team team) {
        this.scorer = scorer;
        this.assister=assister;
        this.team=team;
        timeOfScoreMillis = System.currentTimeMillis();
    }

    /**
     * @return the scorer
     */
    public Player getScorer() {
        return scorer;
    }

    /**
     * @return the assister
     */
    public Player getAssister() {
        return assister;
    }

    /**
     * @param scorer the scorer to set
     */
    public void setScorer(Player scorer) {
        this.scorer = scorer;
    }

    /**
     * @param assister the assister to set
     */
    public void setAssister(Player assister) {
        this.assister = assister;
        this.timeOfScoreMillis = System.currentTimeMillis();
    }
    
    @Override
    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return this.assister.getName()+" to "+ this.scorer.getName() +" at "+sdf.format(new Date(timeOfScoreMillis));
    }

    /**
     * @return the team
     */
    public Team getTeam() {
        return team;
    }

    /**
     * @param team the team to set
     */
    public void setTeam(Team team) {
        this.team = team;
    }
    
}
