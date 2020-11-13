package com.dametdamet.app.model.maze.magicCase;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Case;
import com.dametdamet.app.model.maze.TypeCase;

public class Treasure extends Case {

    private static final int AMOUT_SCORE = 2000;

    public Treasure() {
        super(TypeCase.BONUS);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        super.applyEffect(game, entity);
        if(!isPressed() && entity.isHero()){
            game.addScore(AMOUT_SCORE);
            setPressed(true);
        }
    }
}
