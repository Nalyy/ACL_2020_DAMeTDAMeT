package com.dametdamet.app.model.maze.magicCase;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.entity.TypeEntity;
import com.dametdamet.app.model.maze.Case;
import com.dametdamet.app.model.maze.TypeCase;

public class Heal extends Case {

    private final static int AMOUNT_HEAL = 1;

    public Heal() {
        super(TypeCase.HEAL);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        super.applyEffect(game, entity);
        if(!isPressed() && entity.getType().equals(TypeEntity.HERO)){
            game.healHero(AMOUNT_HEAL);
            setPressed(true);
        }
    }
}
