package com.dametdamet.app.model.entity;

import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.Timer;

public class Hero extends Entity {

    private final int MAX_HP = 3;

    private int hp;
    private Timer invicibiltyTimer;
    private final int RECOVERY_TIME = 3000;


    public Hero(Position position) {

        super(position, TypeEntity.HERO);
        invicibiltyTimer = new Timer();
        hp = MAX_HP;
    }

    public int getHP() {
        return hp;
    }

    public void gainHP(int hpAmont){
        hp += hpAmont;
        if(hp > MAX_HP) hp = MAX_HP;
    }

    public void loseHP(int hpAmont){
        if(invicibiltyTimer.isFinished()){
            hp -= hpAmont;
            invicibiltyTimer.top(RECOVERY_TIME);
        }
        if(hp < 0) hp = 0;
    }

    public int getMAX_HP() {
        return MAX_HP;
    }

    public Timer getInvicibiltyTimer() {
        return invicibiltyTimer;
    }
}
