package com.dametdamet.app.model.entity.monster;

import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.maze.Maze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RandomMonsterTest {

    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;

    Maze maze =  new Maze(WIDTH, HEIGHT);
    /*
     ************************************************
     *
     *             TESTS DE CONSTRUCTION
     *
     ************************************************
     */
    /**
     * On vérifie que le monstre ne se construit pas si on ne lui donne pas de stratégie à utiliser.
     *
     * Erreur : NullPointerException
     */
    @Test
    public void pasDeStrategy(){
        Position initialPosition = new Position(0, 0);
        Assertions.assertThrows(NullPointerException.class, () ->
                { Monster monster = new Monster(initialPosition, null); }
        );
    }

    /**
     * On vérifie que le monstre ne se construit pas si on ne donne pas de labyrinthe à la stratégie
     * qu'il utilise.
     *
     * Erreur : AssertionError
     */
    @Test
    public void strategySansLabyrinthe() {
        RandomMove.INSTANCE.setMaze(null);
        Position initialPosition = new Position(0, 0);
        Assertions.assertThrows(AssertionError.class, () ->
                { Monster monster = new Monster(initialPosition, RandomMove.INSTANCE); }
        );
    }

    /**
     * On vérifie que le monstre ne peut pas prendre une position initiale plus grande en hauteur
     * que celle du labyrinthe.
     *
     * Erreur : AssertionError
     */
    @Test
    public void positionInitialePlusHauteur(){
        RandomMove.INSTANCE.setMaze(maze);
        Position initialPosition = new Position(12, HEIGHT);

        Assertions.assertThrows(AssertionError.class, () ->
        { Monster monster = new Monster(initialPosition, RandomMove.INSTANCE); }
        );

    }

    /**
     * On vérifie que le monstre ne peut pas prendre une position initiale plus petite en hauteur
     * que celle du labyrinthe.
     *
     * Erreur : AssertionError
     */
    @Test
    public void positionInitialeMoinsHauteur(){
        RandomMove.INSTANCE.setMaze(maze);
        Position initialPosition = new Position(0, -1);

        Assertions.assertThrows(AssertionError.class, () ->
                { Monster monster = new Monster(initialPosition, RandomMove.INSTANCE); }
        );
    }

    /**
     * On vérifie que le monstre ne peut pas prendre une position initiale plus petite en largeur
     * que celle du labyrinthe.
     *
     * Erreur : AssertionError
     */
    @Test
    public void positionInitialeMoinsLargeur(){
        RandomMove.INSTANCE.setMaze(maze);
        Position initialPosition = new Position(-1, 8);

        Assertions.assertThrows(AssertionError.class, () ->
                { Monster monster = new Monster(initialPosition, RandomMove.INSTANCE); }
        );
    }

    /**
     * On vérifie que le monstre ne peut pas prendre une position initiale plus grande en largeur
     * que celle du labyrinthe.
     *
     * Erreur : AssertionError
     */
    @Test
    public void positionInitialePlusLargeur(){
        RandomMove.INSTANCE.setMaze(maze);
        Position initialPosition = new Position(WIDTH, 8);

        Assertions.assertThrows(AssertionError.class, () ->
                { Monster monster = new Monster(initialPosition, RandomMove.INSTANCE); }
        );
    }

    /**
     * On vérifie que le monstre s'initialise lorsque tout est OK.
     */
    @Test
    public void positionInitialeOK(){
        RandomMove.INSTANCE.setMaze(maze);
        Position initialPosition = new Position(0, 0);
        Monster monster = new Monster(initialPosition, RandomMove.INSTANCE);

        initialPosition.setX(WIDTH - 1);
        initialPosition.setY(HEIGHT - 1);
        Monster monster2 = new Monster(initialPosition, RandomMove.INSTANCE);
    }

    /*
     ************************************************
     *
     *              TESTS DE DirectionES
     *
     ************************************************
     */

    /**
     * On vérifie que le monstre ne veut pas sortir du labyrinthe de tout en bas (-> faire UP).
     * On vérifie aussi que comme il n'y a pas de murs autour de lui, il accepte de se déplacer.
     */
    @Test
    public void neVaPasHorsLabyUP(){
        RandomMove.INSTANCE.setMaze(maze);
        Position initialPosition = new Position(0, HEIGHT - 1);
        Monster monster = new Monster(initialPosition, RandomMove.INSTANCE);
        monster.setMillisecondsToWait(0);

        // Si le monstre est déjà tout en bas du labyrinthe (= case 19), il ne veut pas encore
        // descendre (= case 20 -> UP de case 19)
        for (int i = 0 ; i < 100; i++){  // à 40 essais (sachant que sa position ne change pas)
                                        // , il devrait forcément réussir à prendre une mauvaise décision
            Direction nextDirection = monster.getNextDirection();
            assert (nextDirection != Direction.DOWN && nextDirection != Direction.IDLE) : nextDirection;
        }
    }

    /**
     * On vérifie que le monstre ne veut pas sortir du labyrinthe de tout en haut (-> faire DOWN).
     * On vérifie aussi que comme il n'y a pas de murs autour de lui, il accepte de se déplacer.
     */
    @Test
    public void neVaPasHorsLabyDOWN(){
        RandomMove.INSTANCE.setMaze(maze);
        Position initialPosition = new Position(0, 0);
        Monster monster = new Monster(initialPosition, RandomMove.INSTANCE);
        monster.setMillisecondsToWait(0);

        // Si le monstre est déjà tout en haut du labyrinthe (= case 0), il ne veut pas encore
        // descendre (= case 0 -> DOWN de case -1)
        for (int i = 0 ; i < 100; i++){
            Direction nextDirection = monster.getNextDirection();
            assert (nextDirection != Direction.UP && nextDirection != Direction.IDLE) : nextDirection;
        }
    }

    /**
     * On vérifie que le monstre ne veut pas sortir du labyrinthe de tout à droite (-> faire RIGHT).
     * On vérifie aussi que comme il n'y a pas de murs autour de lui, il accepte de se déplacer.
     */
    @Test
    public void neVaPasHorsLabyRIGHT(){
        RandomMove.INSTANCE.setMaze(maze);
        Position initialPosition = new Position(WIDTH - 1, 0);
        Monster monster = new Monster(initialPosition, RandomMove.INSTANCE);
        monster.setMillisecondsToWait(0);

        // Si le monstre est déjà tout en haut du labyrinthe (= case 0), il ne veut pas encore
        // descendre (= case 0 -> DOWN de case -1)
        for (int i = 0 ; i < 100; i++){
            Direction nextDirection = monster.getNextDirection();
            assert (nextDirection != Direction.RIGHT && nextDirection != Direction.IDLE) : nextDirection;
        }
    }

    /**
     * On vérifie que le monstre ne veut pas sortir du labyrinthe de tout à gauche (-> faire LEFT).
     * On vérifie aussi que comme il n'y a pas de murs autour de lui, il accepte de se déplacer.
     */
    @Test
    public void neVaPasHorsLabyLEFT(){
        RandomMove.INSTANCE.setMaze(maze);
        Position initialPosition = new Position(0, 0);
        Monster monster = new Monster(initialPosition, RandomMove.INSTANCE);
        monster.setMillisecondsToWait(0);

        // Si le monstre est déjà tout en haut du labyrinthe (= case 0), il ne veut pas encore
        // descendre (= case 0 -> DOWN de case -1)
        for (int i = 0 ; i < 100; i++){
            Direction nextDirection = monster.getNextDirection();
            assert (nextDirection != Direction.LEFT && nextDirection != Direction.IDLE) : nextDirection;
        }
    }

}
