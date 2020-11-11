package com.dametdamet.app.model.entity;

import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.Timer;

public class Hero extends Entity {

    private int hp;
    private Timer invicibiltyTimer;
    private final int RECOVERY_TIME = 3000;


    public Hero(Position position) {

        super(position, TypeEntity.HERO);
        invicibiltyTimer = new Timer();
        hp = 3;
    }

    public int getHP() {
        return hp;
    }

    public void gainHP(int hpAmont){
        hp += hpAmont;
        if(hp > 3) hp = 3;
    }

    public void loseHP(int hpAmont){
        if(invicibiltyTimer.isFinished()){
            hp -= hpAmont;
            invicibiltyTimer.top(RECOVERY_TIME);
        }
        if(hp < 0) hp = 0;
    }
}
