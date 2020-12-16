package com.dametdamet.app.model.graphic;

import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.Timer;

public abstract class GraphicalEffect {

    private int numSprite;
    private int numSpriteMax;
    private int height;
    private int width;

    private Timer timerLifetime;
    private int initialLifetime;

    private Timer timerAnimation;
    private int timeBetweenAnimation;

    private GraphicalEffectType type;

    private Position position;

    public GraphicalEffect(GraphicalEffectType type, Position position,int timeBetweenAnimation, int height, int width, int initialLifetime, int numSpriteMax){
        this.type = type;
        this.position = position;
        this.timeBetweenAnimation = timeBetweenAnimation;
        this.height = height;
        this.width = width;
        this.initialLifetime = initialLifetime;
        this.numSpriteMax = numSpriteMax;
        this.numSprite = 0;

        this.timerLifetime = new Timer();
        this.timerAnimation = new Timer();

        timerLifetime.top(initialLifetime);
        timerAnimation.top(timeBetweenAnimation);

    }

    public boolean isFinished(){
        return timerLifetime.isFinished();
    }

    public boolean isTimerAnimationFinished(){
        return timerAnimation.isFinished();
    }

    public void pauseTimer(){
        timerLifetime.pause();
        timerAnimation.pause();
    }

    public void continueTimer(){
        timerLifetime.continueTimer();
        timerAnimation.continueTimer();
    }

    public void update(){
        if(timerAnimation.isFinished())
            numSprite++;
        if(numSprite > numSpriteMax)
            numSprite = 0;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Position getPosition() {
        return position;
    }

    public GraphicalEffectType getType() {
        return type;
    }

    public int getNumSprite() {
        return numSprite;
    }
}
