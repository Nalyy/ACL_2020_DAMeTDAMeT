package com.dametdamet.app.model.maze.magicCase;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TypeCase;

public class SpawnerChest extends Tile {
    public SpawnerChest() {
        super(TypeCase.SPAWNER_CHEST);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if(!isPressed() && entity.isHero()){
            game.spawnNewChest();
            setPressed(true);
        }
    }
}
