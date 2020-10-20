package com.dametdamet.app.model;

import com.dametdamet.app.engine.Command;

import java.util.Objects;

public class Monster extends Entity{
    private MoveStrategy strategy;

    public Monster(Position position, MoveStrategy strategy){
        super(position);
        Objects.requireNonNull(strategy, "La stratégie donnée à la construction du monstre est null.");
        this.strategy = strategy;
    }

    /**
     * Retourne le prochain mouvement que le monstre veut faire.
     * @return Command
     */
    public Command getNextCommand(){
           return strategy.getNextCommand(this);
    }


}
