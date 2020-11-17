package com.dametdamet.app.model.maze.magicCase;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TypeCase;

public class Time extends Tile {

    private final static int AMOUNT_TIME_MS = 5000;

    public Time() {
        super(TypeCase.TIME);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if(!isPressed() && entity.isHero()){
            game.addTime(AMOUNT_TIME_MS);
            setPressed(true);
        }
    }
}
