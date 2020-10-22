package com.dametdamet.app.model;

import com.dametdamet.app.engine.Command;

import java.util.Objects;

public class Monster extends Entity{
    private MoveStrategy strategy;

    /**
     * Construit un monstre avec une position initiale et une stratégie non nulle.
     * L'objet ne sera pas construit si :
     *         - la stratégie est nulle
     *         - la stratégie n'a pas de labyrinthe
     *         - la position initiale est incorrecte (= hors du labyrinthe)
     *
     * @param position la position initiale du monstre
     * @param strategy la stratégie de déplacement du monstre
     */
    public Monster(Position position, MoveStrategy strategy){
        super(position, TypeEntity.MONSTER);

        // Si pas de stratégie donnée
        Objects.requireNonNull(strategy, "La stratégie donnée à la construction du monstre est null.");

        // Si la stratégie donnée n'a pas de labyrinthe, on doit lancer une erreur.
        assert strategy.hasMaze()  : "La stratégie donnée n'a pas de labyrinthe.";

        // Lance une erreur si position initiale incorrecte
        assert strategy.isInMaze(position) : "La position initiale est hors du labyrinthe.";

        // Si tout ok, on accepte de lui donner la stratégie pour qu'il puisse se construire
        this.strategy = strategy;

    }

    /**
     * Retourne le prochain mouvement que le monstre veut faire.
     *
     * L'attribut strategy ne peut pas être null (cf constructeur).
     * Si la strategy n'a pas de labyrinthe, alors une erreur est lancée dans la fonction appelée.
     *
     * @return Command la prochaine direction que le monstre va prendre
     */
    public Command getNextCommand(){
           return strategy.getNextCommand(this);
    }


}
