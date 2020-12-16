package com.dametdamet.app.model.maze.gameplayTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

public class PiercingshotEnabler extends Tile {

    private int HP_TO_ADD = 1;

    public PiercingshotEnabler(){
        super(TileType.PIERCINGSHOT_ENABLER);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if (!isPressed() && entity.canTrigger(this)){
            entity.addProjectileHP(HP_TO_ADD);
            setPressed(true);
        }
    }
}
