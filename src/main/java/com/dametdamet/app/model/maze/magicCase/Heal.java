package com.dametdamet.app.model.maze.magicCase;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TypeCase;

public class Heal extends Tile {

    private final static int AMOUNT_HEAL = 1;

    public Heal() {
        super(TypeCase.HEAL);
    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if(!isPressed() && entity.isHero()){
            game.healHero(AMOUNT_HEAL);
            setPressed(true);
        }
    }
}
