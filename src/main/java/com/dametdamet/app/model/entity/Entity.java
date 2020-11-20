package com.dametdamet.app.model.entity;

import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

import java.util.Collection;

public abstract class Entity {
    private Position position;

    private EntityType type;

    public Entity(Position position, EntityType type){
        this.position = position;
        this.type = type;
    }

    /**
     * Remplace la position avec la nouvelle position.
     * @param position
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
        Collection<TileType> limitationTiles = this.type.getLimitations();
        TileType tileType = tile.getType();

        return !limitationTiles.contains(tileType);
    }

    public boolean canTrigger(Tile tile){
        Collection<TileType> tilesToTrigger = type.getTriggers();
        TileType tileType = tile.getType();

        return tilesToTrigger.contains(tileType);
    }
}
