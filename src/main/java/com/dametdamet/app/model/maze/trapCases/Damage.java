package com.dametdamet.app.model.maze.trapCases;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TypeCase;

public class Damage extends Tile {

    private final static int AMOUNT_HEAL = 1;

    public Damage() {
        super(TypeCase.DAMAGE);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if(!isPressed() && entity.isHero()){
            game.hurtHero(AMOUNT_HEAL);
            setPressed(true);
        }
    }
}
