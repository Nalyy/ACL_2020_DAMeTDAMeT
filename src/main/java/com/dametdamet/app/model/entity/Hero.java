package com.dametdamet.app.model.entity;

import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.Timer;

public class Hero extends Entity {

    private final int maxHp;

    private int hp;
    private Timer invicibiltyTimer;
    private final int RECOVERY_TIME = 3000;


    public Hero(Position position,int maxHp) {
        super(position, TypeEntity.HERO);
        invicibiltyTimer = new Timer();
        hp = maxHp;
        this.maxHp = maxHp;
    }

    public int getHP() {
        return hp;
    }

    /**
     * ajoute le nombre de point de vie en paramètre (ne peut pas dépasser MAX_HP)
     * @param hpAmont nombre de point de vie à ajouter (doit être positif sinon aucun effet)
     */
    public void gainHP(int hpAmont){

        if(hpAmont > maxHp) hpAmont = maxHp;
        if(hpAmont < 0) hpAmont = 0;

        hp += hpAmont;
        if(hp > maxHp) hp = maxHp;
        if(hp < 0) hp = 0;
    }

    /**
     * retire le nombre de point de vie en paramètre si le timer d'invincibilité est fini (ne peut pas descendre en dessous de 0)
     * @param hpAmont nombre de point de vie à retirer (doit être positif sinon aucun effet)
     */
    public void loseHP(int hpAmont){
        if(invicibiltyTimer.isFinished()){
            if(hpAmont > maxHp) hpAmont = maxHp;
            if(hpAmont < 0) hpAmont = 0;

            hp -= hpAmont;
            invicibiltyTimer.top(RECOVERY_TIME);
        }
        if(hp > maxHp) hp = maxHp;
        if(hp < 0) hp = 0;
    }

    /**
     *
     * @return le nombre de point de vie maximum
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * renvoie l'instance du timer d'invincibilté du héros
     * @return timer d'invincibilté du héros
     */
    public Timer getInvicibiltyTimer() {
        return invicibiltyTimer;
    }
}
