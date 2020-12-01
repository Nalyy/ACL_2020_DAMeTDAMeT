package com.dametdamet.app.model.maze.normalTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

public class Stairs extends Tile {

    public Stairs() {
        super(TileType.STAIRS);
    }

    public void applyEffect(PacmanGame game, Entity entity){
        if (entity.canTrigger(this)){
            game.goToNextLevel();
        }
    }
}
