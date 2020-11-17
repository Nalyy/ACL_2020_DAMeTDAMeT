package com.dametdamet.app.model.maze.magicCase;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TypeCase;

public class Treasure extends Tile {

    private static final int AMOUT_SCORE = 2000;

    public Treasure() {
        super(TypeCase.BONUS);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if(!isPressed() && entity.isHero()){
            game.addScore(AMOUT_SCORE);
            setPressed(true);
        }
    }
}
