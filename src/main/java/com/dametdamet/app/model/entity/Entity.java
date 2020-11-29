package com.dametdamet.app.model.entity;

import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

import java.util.Collection;
import java.util.Objects;

public abstract class Entity {
    private Position position;

    protected EntityType type;

    public Entity(Position position, EntityType type){
        this.position = position;
        this.type = type;
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
}
