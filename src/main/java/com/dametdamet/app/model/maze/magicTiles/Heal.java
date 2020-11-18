package com.dametdamet.app.model.maze.magicTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

public class Heal extends Tile {

    private final static int AMOUNT_HEAL = 1;

    public Heal() {
        super(TileType.HEAL);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if(!isPressed() && entity.isHero()){
            game.healHero(AMOUNT_HEAL);
            setPressed(true);
        }
    }
}
