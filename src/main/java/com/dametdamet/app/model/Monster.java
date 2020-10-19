package com.dametdamet.app.model;

import com.dametdamet.app.engine.Command;

public class Monster {
    private MoveStrategy strategy;

    public Monster(MoveStrategy strategy){
        this.strategy = strategy;
    }

    /**
     * Retourne le prochain mouvement que le monstre veut faire.
     * @return Command
     */
    public Command getNextCommand(){
           return Command.IDLE;
    }


}
