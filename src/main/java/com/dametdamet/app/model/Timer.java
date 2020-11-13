package com.dametdamet.app.model;

/**
 * @author Maxime Choné
 *
 * classe qui permet de lancer un minuteur
 */
public class Timer {

    private long startTime;
    private long timerTime;
    private boolean isPaused;

    public Timer(){
        this.startTime = System.currentTimeMillis() - timerTime;
        isPaused = false;
    }

    /**
     * lance le top du minuteur
     * @param timerTime temps du minuteur en milliseconde
     */
    public void top(long timerTime){
        this.timerTime = timerTime;
        isPaused = false;
        startTime = System.currentTimeMillis();
    }


    /**
     * met le timer en pause si il n'est pas déjà en pause
     */
    public void pause(){
        if(!isPaused) {
            isPaused = true;
            timerTime = getTime();
        }
    }

    /**
     * relance le timer si il était en pause
     */
    public void continueTimer(){
        if(isPaused){
            isPaused = false;
            startTime = System.currentTimeMillis();
        }
    }

    /**
     * donne le temps restant du timer en milliseconde
     * @return le temps restant du timer en milliseconde
     */
    public long getTime(){
        return (timerTime - (System.currentTimeMillis() - startTime))< 0 ? 0:timerTime - (System.currentTimeMillis() - startTime);
    }

    /**
     * vérifie si le minuteur est terminé
     * @return true si le temps écoulé entre le top et maintenant est supérieur ou égal au timerTime et que le timer n'est pas en pause, sinon faux
     */
    public boolean isFinished() {

        return !isPaused && System.currentTimeMillis() - startTime >= timerTime;
    }
}
