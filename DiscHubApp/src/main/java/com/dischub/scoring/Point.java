/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dischub.scoring;

import com.dischub.tournament.Player;

/**
 *
 * @author rodtr
 */
public class Point {

    private Player scorer;
    private Player assister;
    private long timeOfScoreMillis;
    
    public Point(){
        
    }
    
    public Point(Player scorer, Player assister) {
        this.scorer = scorer;
        this.assister=assister;
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
    
    
    
}
