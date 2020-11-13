package com.dametdamet.app.model.maze.magicCase;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.entity.TypeEntity;
import com.dametdamet.app.model.maze.Case;
import com.dametdamet.app.model.maze.TypeCase;

public class Time extends Case {

    private final static int AMOUNT_TIME_MS = 5000;

    public Time() {
        super(TypeCase.TIME);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        super.applyEffect(game, entity);
        if(!isPressed() && entity.getType().equals(TypeEntity.HERO)){
            game.addTime(AMOUNT_TIME_MS);
            setPressed(true);
        }
    }
}
