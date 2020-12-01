package com.dametdamet.app.model.maze.magicTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

public class Treasure extends Tile {

    private static final int AMOUT_SCORE = 2000;

    public Treasure() {
        super(TileType.BONUS);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if(!isPressed() && entity.canTrigger(this)){
            game.addScore(AMOUT_SCORE);
            setPressed(true);
        }
    }
}
