package com.dametdamet.app.model.graphic;

import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.Timer;

public abstract class GraphicalEffect {

    private int num_sprite;
    private int num_sprite_max;
    private int height;
    private int width;

    private Timer timer_lifetime;
    private int initial_lifetime;

    private Timer timer_animation;
    private int time_between_animation;

    private GraphicalEffectType type;

    private Position position;

    public GraphicalEffect(GraphicalEffectType type, Position position,int time_between_animation, int height, int width, int initial_lifetime,int num_sprite_max){
        this.type = type;
        this.position = position;
        this.time_between_animation = time_between_animation;
        this.height = height;
        this.width = width;
        this.initial_lifetime = initial_lifetime;
        this.num_sprite_max = num_sprite_max;
        this.num_sprite = 0;

        this.timer_lifetime = new Timer();
        this.timer_animation = new Timer();

        timer_lifetime.top(initial_lifetime);
        timer_animation.top(time_between_animation);

    }

    public boolean isFinished(){
        return timer_lifetime.isFinished();
    }

    public boolean isTimerAnimationFinished(){
        return timer_animation.isFinished();
    }

    public void pauseTimer(){
        timer_lifetime.pause();
        timer_animation.pause();
    }

    public void continueTimer(){
        timer_lifetime.continueTimer();
        timer_animation.continueTimer();
    }

    public void update(){
        if(timer_animation.isFinished())
            num_sprite++;
        if(num_sprite > num_sprite_max)
            num_sprite = 0;
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

    public int getNum_sprite() {
        return num_sprite;
    }
}
