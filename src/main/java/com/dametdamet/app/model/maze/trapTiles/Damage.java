package com.dametdamet.app.model.maze.trapTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

public class Damage extends Tile {

    private final static int AMOUNT_HEAL = 1;

    public Damage() {
        super(TileType.DAMAGE);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if(!isPressed() && entity.canTrigger(this)){
            game.hurtHero(AMOUNT_HEAL);
            setPressed(true);
        }
    }
}
