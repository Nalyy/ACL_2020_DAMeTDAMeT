package com.dametdamet.app.model;

/**
 * @author Maxime Choné
 *
 * classe qui permet de lancer un minuteur
 */
public class Timer {

    private long startTime;
    private long timerTime;



    public Timer(){
        this.startTime = System.currentTimeMillis() - timerTime;
    }


    public void setTimerTime(long timerTime) {
        this.timerTime = timerTime;
    }

    /**
     * lance le top du minuteur
     * @param timerTime temps du minuteur en milliseconde
     */
    public void top(long timerTime){
        this.timerTime = timerTime;
        startTime = System.currentTimeMillis();
    }

    /**
     * vérifie sur le minuteur est terminé
     * @return true si le temps écoulé entre le top et maintenant est supérieur ou égal au timerTime, sinon faux
     */
    public boolean isFinished() {
        return System.currentTimeMillis() - startTime >= timerTime;
    }
}
