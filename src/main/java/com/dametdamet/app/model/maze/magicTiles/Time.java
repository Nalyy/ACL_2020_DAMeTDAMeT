package com.dametdamet.app.model.maze.magicTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

public class Time extends Tile {

    private final static int AMOUNT_TIME_MS = 5000;

    public Time() {
        super(TileType.TIME);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if(!isPressed() && entity.isHero()){
            game.addTime(AMOUNT_TIME_MS);
            setPressed(true);
        }
    }
}
