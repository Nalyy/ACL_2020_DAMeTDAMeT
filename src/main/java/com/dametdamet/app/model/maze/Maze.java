package com.dametdamet.app.model.maze;

import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.dao.factory.AbstractDAOFactory;

public class Maze{
    private Case[][] maze;
    private int width = 20;
    private int height = 20;

    /**
     * Initialise un Maze vide
     */
    public Maze(){
        maze = new Case[width][height];
        generate();
    }

    /**
     * Initialise un maze vide de taille width*height
     * @param width largeur du labyrinthe
     * @param height hauteur du labyrinthe
     */
    public Maze(int width, int height){
        this.width = width;
        this.height = height;
        maze = new Case[width][height];
        generate();
    }

    /**
     * Génère le labyrinthe à partir d'un fichier, si le fichier est incorrect alors on génère un labyrinthe vide
     * @param nomFichier nom du fichier qui contient un labyrinthe
     */
    public Maze(String nomFichier){
        generate(nomFichier);
    }

    /**
     * Génère un labyrinthe à partir d'un nom de fichier
     * @param nomFichier le nom du fichier qui contient le labyrinthe
     */
    private void generate(String nomFichier) {
        maze = AbstractDAOFactory.getAbstractDAOFactory(AbstractDAOFactory.TXT).getFileDAO().load(nomFichier);
        if(maze == null || maze.length == 0){
            maze = new Case[width][height];
            generate();
        }
        width = maze.length;
        height = maze[0].length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Génère un labyrinthe vide.
     */
    private void generate() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
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

        boolean isWithin = (x < width && x >= 0) && (y < height && y >= 0);

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
}
