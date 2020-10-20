package com.dametdamet.app.model;

import com.dametdamet.app.engine.Command;

public class Monster extends Entity{
    private MoveStrategy strategy;

    public Monster(Position position, MoveStrategy strategy){
        super(position,TypeEntity.MONSTER);
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
