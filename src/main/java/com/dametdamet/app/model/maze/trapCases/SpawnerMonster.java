package com.dametdamet.app.model.maze.trapCases;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TypeCase;

public class SpawnerMonster extends Tile {

    public SpawnerMonster() {
        super(TypeCase.SPAWNER_MONSTERS);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if (!isPressed() && entity.isHero()) {
            game.spawnNewMonster();
            setPressed(true);
        }
    }
}
