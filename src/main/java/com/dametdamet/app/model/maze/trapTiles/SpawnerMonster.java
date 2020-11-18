package com.dametdamet.app.model.maze.trapTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

public class SpawnerMonster extends Tile {

    public SpawnerMonster() {
        super(TileType.SPAWNER_MONSTERS);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if (!isPressed() && entity.isHero()) {
            game.spawnNewMonster();
            setPressed(true);
        }
    }
}
