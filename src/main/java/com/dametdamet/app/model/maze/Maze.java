package com.dametdamet.app.model.maze;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.maze.magicCase.Treasure;
import com.dametdamet.app.model.maze.normalTiles.Empty;
import com.dametdamet.app.model.maze.normalTiles.OutOfBound;

import java.util.*;

public class Maze{
    private Tile[][] maze;
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
        maze = new Tile[DEFAULT_WIDTH][DEFAULT_HEIGHT];
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
        maze = new Tile[width][height];
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

    public void setMaze(Tile[][] maze){
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
                maze[x][y] = new Empty();
            }
        }
    }

    /**
     * Retourne le type de la case à la position donnée.
     * @param position position de la case que l'on cherche
     * @return la case correspondante
     */
    public Tile whatIsIn(Position position){
        int x = position.getX();
        int y = position.getY();

        boolean isWithin = (x < this.getWidth() && x >= 0) && (y < this.getHeight() && y >= 0);

        return isWithin ? maze[x][y] : new OutOfBound();
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

    /**
     * Ajoute une position dans la liste des positions possibles pour les coffres
     * @param posChest la position que l'on vas rajouter dans la liste
     */
    public void addNewPositionChest(Position posChest){
        this.positionBonusChest.add(posChest);
        Collections.shuffle(positionBonusChest); // On mélange la liste pour ne pas faire apparaitre en ligne
    }

    /**
     * Ajoute une nouvelle position où un monstre peut apparaître dans le labyrinthe.
     *
     * @param position une position possible où un monstre peut apparaître
     */
    public void addNewPositionMonster(Position position){
        this.positionMonsters.add(position);
    }

    /**
     * Ajoute un monstre dans le monde s'il a un endroit où apparaître.
     *
     * @param pacmanGame le jeu qui possède le labyrinthe et qui accueillera peut-être un monstre supplémentaire
     */
    public void addNewMonster(PacmanGame pacmanGame){
        Position position = getAMonsterPosition(positionMonsters.size());
        if (position!= null){
            pacmanGame.addMonster(position);
        }

    }

    /**
     * @return la position d'une case vide dans le labyrinthe
     */
    private Position getAnEmptyCase(){
        Position position = new Position(0, 0);
        Stack<Position> emptyTiles = new Stack<>();

        /* On commence par construire la liste des cases vides du labyrinthe */
        for (int x = 0; x < getWidth() ; x++){
            for (int y = 0; y < getHeight(); y++){
                position.setX(x);
                position.setY(y);

                Tile tile = whatIsIn(position);

                if (tile.getType().equals(TypeCase.EMPTY)){
                    emptyTiles.push(new Position(position));
                }
            }
        }

        /* S'il n'y avait aucune case vide */
        if (emptyTiles.size() == 0){
            position = null;
        }else {
            /* On mélange la collection pour retourner une case vide aléatoire */
            Collections.shuffle(emptyTiles);
            position = emptyTiles.pop();
        }

        return position;
    }

    /**
     * Donne une position valide et possiblement aléatoire pour qu'un monstre apparaisse dans le labyrinthe.
     *
     * @param sizeOfCollection : taille de la collection des positions des monstres
     * @return une position où le monstre peut apparaître
     */
    private Position getAMonsterPosition(int sizeOfCollection){
        Position position;

        /* Si il n'y a pas de positions données pour le spawn de monstres,
        * on prend une case vide aléatoire dans le labyrinthe. */
        if (sizeOfCollection == 0 ){
            position = getAnEmptyCase();

        /* Sinon, on en prend une aléatoirement dans la liste. */
        }else {
            Collections.shuffle(positionMonsters);
            position = positionMonsters.get(0);
        }

        return position;
    }

    /**
     * Ajoute un nouveau coffre bonus dans le labyrinthe
     */
    public void addNewChest() {
        Position position = getAChestPosition();
        if (position!= null){
            maze[position.getX()][position.getY()] = new Treasure();
        }
    }

    /**
     * Permet de retourner une position de la liste des coffres ou une position aléatoire du labyrinthe
     * @return une position d'une case vide pour le coffre
     */
    private Position getAChestPosition(){
        Position position;

        /* Si il n'y a pas d'enplacement pour les nouveaux coffres, on prend une case vide aléatoire dans le labyrinthe. */
        if (positionBonusChest.size() == 0 ){
            position = getAnEmptyCase();
        }else { /* Sinon, on en prend une aléatoirement dans la liste. */
            position = positionBonusChest.pop();
        }

        return position;
    }
}
