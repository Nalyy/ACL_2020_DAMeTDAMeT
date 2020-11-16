package com.dametdamet.app.model.maze.trapCases;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Case;
import com.dametdamet.app.model.maze.TypeCase;

public class SpawnerMonster extends Case {

    public SpawnerMonster() {
        super(TypeCase.SPAWNER_MONSTERS);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        super.applyEffect(game, entity);
        if (!isPressed() && entity.isHero()) {
            game.spawnNewMonster();
            setPressed(true);
        }
    }
}
