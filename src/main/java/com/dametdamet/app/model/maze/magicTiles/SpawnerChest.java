package com.dametdamet.app.model.maze.magicTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

public class SpawnerChest extends Tile {
    public SpawnerChest() {
        super(TileType.SPAWNER_CHEST);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if(!isPressed() && entity.canTrigger(this)){
            game.spawnNewChest();
            setPressed(true);
        }
    }
}
