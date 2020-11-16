package com.dametdamet.app.model.maze;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.monster.RandomMove;
import com.dametdamet.app.model.maze.magicCase.Treasure;

import java.util.*;

public class Maze{
    private Case[][] maze;
    private static final int DEFAULT_WIDTH = 20;
    private static final int DEFAULT_HEIGHT = 20;
    public static final int DEFAULT_NB_MONSTERS = 5;

    private Position initialPositionPlayer;
    private Collection<Position> initialPositionMonster;

    private Stack<Position> positionBonusChest;
    private List<Position> positionMonsters;

    /**
     * Initialise un Maze vide
     */
    public Maze(){
        maze = new Case[DEFAULT_WIDTH][DEFAULT_HEIGHT];
        initialPositionMonster = new ArrayList<>();
        positionBonusChest = new Stack<>();
        positionMonsters = new ArrayList<>();
        generate();
        this.setInitialPositionPlayer(new Position(0,0));
    }

    /**
     * Initialise un maze vide de taille width*height
     * @param width largeur du labyrinthe
     * @param height hauteur du labyrinthe
     */
    public Maze(int width, int height){
        maze = new Case[width][height];
        initialPositionMonster = new ArrayList<>();
        generate();
        this.setInitialPositionPlayer(new Position(0,0));
    }

    /**
     * Définit la position du joueur quand il apparaitras dans le labyrinthe
     * @param initialPositionPlayer position initiale du joueur quand il apparaitras
     */
    public void setInitialPositionPlayer(Position initialPositionPlayer) {
        if(!isNotWall(initialPositionPlayer)){ // Si le joueur est dans un mur
            Position initialPos = new Position(initialPositionPlayer.getX(), initialPositionPlayer.getY());
            boolean isFinished = false;

            while(!isNotWall(initialPos) && !isFinished){
                initialPos.setX(initialPos.getX() + 1);

                if(initialPos.getX() >= this.getWidth()){ // On check les bornes de X
                    initialPos.setY(initialPos.getY() + 1);
                    initialPos.setX(0);
                    isFinished = initialPos.getY() >= this.getHeight(); // Si on arrive à la fin du maze on termine tout
                }
            }
            if(isFinished){ // Si on a pas trouvé un meilleur emplacement on prends celui donné de base
                this.initialPositionPlayer = initialPositionPlayer;
            }else{
                this.initialPositionPlayer = initialPos;
            }
        }else{
            this.initialPositionPlayer = initialPositionPlayer;
        }
    }

    public Position getInitialPositionPlayer() {
        return initialPositionPlayer;
    }

    public void addInitialMonsterPosition(Position initialPositionMonster){
        this.initialPositionMonster.add(initialPositionMonster);
    }

    public Iterator<Position> getIteratorMonsterPositions(){
        return initialPositionMonster.iterator();
    }

    public void setMaze(Case[][] maze){
        this.maze = maze;
    }

    public int getWidth() {
        return maze.length;
    }

    public int getHeight() {
        return maze[0].length;
    }

    /**
     * Génère un labyrinthe vide.
     */
    private void generate() {
        for (int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                maze[x][y] = new Case(TypeCase.EMPTY);
            }
        }
    }

    /**
     * Retourne le type de la case à la position donnée.
     * @param position position de la case que l'on cherche
     * @return la case correspondante
     */
    public Case whatIsIn(Position position){
        int x = position.getX();
        int y = position.getY();

        boolean isWithin = (x < this.getWidth() && x >= 0) && (y < this.getHeight() && y >= 0);

        return isWithin ? maze[x][y] : new Case(TypeCase.OUTOFBOUND);
    }

    /**
     * Retourne vrai si la position correspond à une case qui n'est pas un mur et existante.
     * @param position postion de la case que l'on veut regarder
     * @return vrai si la position correspond à une case qui n'est pas un mur et existante.
     */
    public boolean isNotWall(Position position){
        return whatIsIn(position).getType() != TypeCase.WALL && isNotOutOfBound(position);
    }

    /**
     * Retourne vrai si la position correspond à une case qui est existante.
     * @param position postion de la case que l'on veut regarder
     * @return vrai si la position correspond à une case qui est existante.
     */
    public boolean isNotOutOfBound(Position position){
        return whatIsIn(position).getType() != TypeCase.OUTOFBOUND;
    }

    public void addNewPositionChest(Position posChest){
        this.positionBonusChest.add(posChest);
        Collections.shuffle(positionBonusChest); // On mélange la liste pour ne pas faire apparaitre en ligne
    }

    public void addNewPositionMonster(Position position){
        this.positionMonsters.add(position);
        Collections.shuffle(positionMonsters);
    }

    public void addNewMonster(PacmanGame pacmanGame){
        Position position = getAMonsterPosition(positionMonsters.size());
        if (position!= null){
            pacmanGame.addMonster(position);
        }

    }

    private Position getAMonsterPosition(int sizeOfCollection){
        Position position;

        /* Si il n'y a pas de positions données pour le spawn de monstres,
        * on prend une position aléatoire dans le labyrinthe. */
        if (sizeOfCollection == 0 ){
            Random rand = new Random();
            int numberOfTiles = getWidth() * getHeight();
            int i = 0;

            /* On cherche une position tant qu'il y a encore des positions à tester. */
            do{
                i++;
                position = new Position(rand.nextInt()%getWidth(),rand.nextInt()%getHeight());
                System.out.println(i);
            }while (!isNotWall(position) && i<numberOfTiles);

            /* Si on est sortis de la boucle parce qu'il n'y a plus de positions
            *  à tester et rien n'est correct, alors il n'y a pas de positions
            *  pour le monstre. */
            if (i >= numberOfTiles){
                position = null;
            }
        }else {
            position = positionMonsters.get(0);
        }

        return position;
    }

    public void addNewChest() {
        Position pos;
        if(positionBonusChest.size() == 0){
            Random rand = new Random();
            do{
                pos = new Position(rand.nextInt()%getWidth(),rand.nextInt()%getHeight());
            }while (isNotWall(pos));
            maze[pos.getX()][pos.getY()] = new Treasure();
        }else{
            pos = positionBonusChest.pop();
            if(this.isNotWall(pos)){
                maze[pos.getX()][pos.getY()] = new Treasure();
            }
        }
    }
}
