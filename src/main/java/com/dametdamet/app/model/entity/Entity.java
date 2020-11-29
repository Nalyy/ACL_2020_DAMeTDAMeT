package com.dametdamet.app.model.entity;

import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.Timer;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

import java.util.Collection;
import java.util.Objects;

public abstract class Entity {
    private Position position;

    protected EntityType type;


    protected int maxHp = 1;

    protected int hp = maxHp;
    protected Timer invicibiltyTimer;
    protected int recoveryTime = 0;

    public Entity(Position position, EntityType type){
        this.position = position;
        this.type = type;
        invicibiltyTimer = new Timer();
    }

    /**
     * Remplace la position avec la nouvelle position.
     * @param position la nouvelle position
     */
    public void moveTo(Position position){
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }


    /**
     * Retourne la position.
     * @return position
     */
    public Position getPosition() {
        return position;
    }

    public EntityType getType() {
        return type;
    }

    public boolean canGoTo(Tile tile){
        if(tile == null) return false;
        Collection<TileType> limitationTiles = this.type.getLimitations();
        TileType tileType = tile.getType();

        return !limitationTiles.contains(tileType);
    }

    public boolean canTrigger(Tile tile){
        if(tile == null) return false;
        Collection<TileType> tilesToTrigger = type.getTriggers();
        TileType tileType = tile.getType();

        return tilesToTrigger.contains(tileType);
    }

    public int getHP() {
        return hp;
    }

    /**
     * ajoute le nombre de point de vie en paramètre (ne peut pas dépasser MAX_HP)
     * @param hpAmount nombre de point de vie à ajouter (doit être positif sinon aucun effet)
     */
    public void gainHP(int hpAmount){

        if(hpAmount > maxHp) hpAmount = maxHp;
        if(hpAmount < 0) hpAmount = 0;

        hp += hpAmount;
        if(hp > maxHp) hp = maxHp;
        if(hp < 0) hp = 0;
    }

    /**
     * retire le nombre de point de vie en paramètre si le timer d'invincibilité est fini (ne peut pas descendre en dessous de 0)
     * @param hpAmount nombre de point de vie à retirer (doit être positif sinon aucun effet)
     */
    public void loseHP(int hpAmount){
        if(invicibiltyTimer.isFinished()){
            if(hpAmount > maxHp) hpAmount = maxHp;
            if(hpAmount < 0) hpAmount = 0;

            hp -= hpAmount;
            invicibiltyTimer.top(recoveryTime);
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
