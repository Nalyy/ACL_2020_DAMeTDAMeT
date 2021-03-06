package com.dametdamet.app.model.maze;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;

import java.util.Objects;

public abstract class Tile {
    private final TileType type;
    private int numSprite;
    private boolean pressed;

    /**
     * Crée une case de type donné avec le numéro de sprite donné
     * @param type type de la case initialisé
     * @param numSprite numéro du sprite actuel de la case
     */
    public Tile(TileType type, int numSprite){
        this.numSprite = numSprite;
        this.type = type;
        this.pressed = false;
    }

    /**
     * Crée une case de type donné avec un numéro de sprite à 0
     * @param type type de la case initialisé
     */
    public Tile(TileType type){
        this.type = type;
        this.numSprite = 0;
        this.pressed = false;
    }

    public abstract void applyEffect(PacmanGame game, Entity entity);

    /**
     * Retourne le type de la case.
     * @return TypeCase
     */
    public TileType getType(){
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile aCase = (Tile) o;
        return type == aCase.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    public int getNumSprite() {
        return numSprite;
    }

    public void setNumSprite(int numSprite){
        this.numSprite = numSprite;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
        checkPressed();
    }

    private void checkPressed(){
        numSprite = isPressed()?1:0;
    }

    public boolean isPressed() {
        return pressed;
    }
}
