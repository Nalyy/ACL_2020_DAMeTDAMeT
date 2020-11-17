package com.dametdamet.app.model.maze.normalTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TypeCase;

public class Stairs extends Tile {

    public Stairs() {
        super(TypeCase.STAIRS);
    }

    public void applyEffect(PacmanGame game, Entity entity){
        if (entity.isHero()){
            game.goToNextLevel();
        }
    }
}
