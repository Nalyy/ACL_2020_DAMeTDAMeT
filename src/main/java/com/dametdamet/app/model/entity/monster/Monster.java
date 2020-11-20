package com.dametdamet.app.model.entity.monster;

import com.dametdamet.app.engine.Command;
import com.dametdamet.app.model.*;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.entity.EntityType;

import java.util.Objects;

public class Monster extends Entity {
    private MoveStrategy strategy;
    private Timer timer;
    private long millisecondsToWait = 400; // par défaut, chaque monstre bouge après 0.5 secondes d'attente

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
        super(position, EntityType.MONSTER);

        // Lance une erreur si la stratégie donnée est null
        Objects.requireNonNull(strategy, "La stratégie donnée à la construction du monstre est null.");

        // Lance une erreur si la stratégie donnée n'a pas de labyrinthe
        assert strategy.hasMaze()  : "La stratégie donnée n'a pas de labyrinthe.";

        // Lance une erreur si position initiale incorrecte
        assert strategy.isInMaze(position) : "La position initiale est hors du labyrinthe.";

        // Si tout ok, on accepte de lui donner la stratégie pour qu'il puisse se construire.
        this.strategy = strategy;

        // On donne son timer au monstre et on le démarre.
        timer = new Timer();
        resetTimer();

    }

    public Monster(Position position, MoveStrategy strategy, EntityType type){
        this(position, strategy);
        this.type = type;
    }

    /**
     * Retourne le prochain mouvement que le monstre veut faire.
     *
     * L'attribut strategy ne peut pas être null (cf constructeur).
     * Si la strategy n'a pas de labyrinthe, alors une erreur est lancée dans la fonction appelée.
     *
     * @return Command la prochaine direction que le monstre va prendre
     */
    public Direction getNextDirection(){
           return strategy.getNextDirection(this);
    }

    /**
     * Retourne vrai si le timer du monstre a atteint la valeur de l'attribut millisecondsToWait.
     * @return vrai si le timer du monstre est fini
     */
    public boolean isFinishedTimer(){
        return timer.isFinished();
    }

    /**
     * Reset le timer du monstre.
     */
    public void resetTimer(){
        timer.top(millisecondsToWait);
    }

    /**
     * Met à jour le nombre de milliseconds que le monstre attend avant de pouvoir se déplacer à nouveau.
     * @param milliseconds le nombre de millisecondes
     */
    public void setMillisecondsToWait(int milliseconds){
        assert (milliseconds >= 0) : "Il faut un nombre de millisecondes positif.";
        millisecondsToWait = milliseconds;
        timer.top(milliseconds);
    }

}
