package com.dametdamet.app.model.maze.normalTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

public class Teleportation extends Tile {

    private Position destination;

    public Teleportation(Position dest) {
        super(TileType.TELEPORTATION);
        this.destination = dest;
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        entity.moveTo(destination);
    }

    public Position getDestination(){
        return destination;
    }
}