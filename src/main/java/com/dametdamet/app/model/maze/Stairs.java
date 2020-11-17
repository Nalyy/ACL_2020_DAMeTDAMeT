package com.dametdamet.app.model.maze;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.entity.Entity;

public class Stairs extends Case{

    public Stairs() {
        super(TypeCase.STAIRS);
    }

    public void applyEffect(PacmanGame game, Entity entity){
        if (entity.isHero()){
            game.goToNextLevel();
        }
    }
}
