package com.dametdamet.app.model.leaderboard;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;

public class Leaderboard implements Serializable, Iterable<Score> {

    private final AbstractList<Score> scores;

    private final int limiteTaille;

    /**
     * Construit un leaderboard avec une taille maximale de 10
     */
    public Leaderboard() {
        this.limiteTaille = 10;
        scores = new ArrayList<>(limiteTaille);
    }

    /**
     * Construit un leaderboard avec une taille maximale en paramètre
     * @param limiteTaille taille maximale tu leaderboard
     */
    public Leaderboard(int limiteTaille){
        this.limiteTaille = limiteTaille;
        scores = new ArrayList<>(limiteTaille);
    }

    /**
     * Ajoute un score au leaderboard
     * @param score score au leaderboard
     */
    public void add(Score score){
        Score oldScore;
        boolean isInLeaderboard = false;

        for(int i = 0; i < scores.size(); i++){
            oldScore = scores.get(i);
            isInLeaderboard = score.getValue() > oldScore.getValue();
            if(isInLeaderboard){
                scores.set(i, score);
                i++; // Pour éviter d'écraser le nouveau score
                while(i < scores.size() && i < limiteTaille){ // On décale tout les scores d'avant
                    scores.set(i, oldScore);
                    oldScore = scores.get(i);
                    i++;
                }
                if(i == scores.size() && i < limiteTaille){
                    scores.add(oldScore);
                }
            }
        }

        if(!isInLeaderboard && scores.size() < limiteTaille){
            scores.add(score);
        }
    }

    @Override
    public Iterator<Score> iterator(){
        return scores.iterator();
    }

    @Override
    public String toString() {
        StringBuilder strbuild = new StringBuilder(15 + 23*limiteTaille);
        strbuild.append("Leaderboard : \n");
        int i = 0;
        for(Score score: scores){
            strbuild.append(++i)
                    .append(": ")
                    .append(score.toString())
                    .append("\n");
        }
        return strbuild.toString();
    }
}
