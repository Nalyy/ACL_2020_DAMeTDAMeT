package com.dametdamet.app.model.maze.magicCase;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Case;
import com.dametdamet.app.model.maze.TypeCase;

public class SpawnerChest extends Case {
    public SpawnerChest() {
        super(TypeCase.SPAWNER_CHEST);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        super.applyEffect(game, entity);
        if(!isPressed() && entity.isHero()){
            game.spawnNewChest();
            setPressed(true);
        }
    }
}
