package com.dametdamet.app.model.maze.trapCases;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.entity.TypeEntity;
import com.dametdamet.app.model.maze.Case;
import com.dametdamet.app.model.maze.TypeCase;

public class Damage extends Case {

    private final static int AMOUNT_HEAL = 1;

    public Damage() {
        super(TypeCase.DAMAGE);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        super.applyEffect(game, entity);
        if(!isPressed() && entity.getType().equals(TypeEntity.HERO)){
            game.hurtHero(AMOUNT_HEAL);
            setPressed(true);
        }
    }
}
