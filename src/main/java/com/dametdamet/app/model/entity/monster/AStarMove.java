package com.dametdamet.app.model.entity.monster;

import com.dametdamet.app.model.Direction;
import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Maze;

import java.util.*;

public enum AStarMove implements MoveStrategy{
    INSTANCE;

    private Monster monster;
    private PacmanGame game;
    private Maze maze;

    private List<AStarTile> allTiles;
    private List<AStarTile> toBeSeen;
    private List<AStarTile> alreadySeen;

    @Override
    public void setGame(PacmanGame game) {
        this.game = game;
        maze = game.getMaze();
    }

    @Override
    public boolean hasMaze() {
        return maze!=null;
    }

    @Override
    public boolean isInMaze(Position position) {
        int x = position.getX();
        int y = position.getY();
        int width = maze.getWidth();
        int height = maze.getHeight();

        boolean isWithinWitdh = (x >= 0) && (x < width);
        boolean isWithinHeight = (y >= 0) && (y < height);

        return isWithinWitdh && isWithinHeight;
    }

    @Override
    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public Direction getNextDirection(Entity monster) {
        Objects.requireNonNull(game, "La stratégie appliquée à l'ennemi n'a pas de jeu associé.");
        Objects.requireNonNull(maze, "La stratégie appliquée à l'ennemi n'a pas de labyrinthe associé.");

        allTiles = new ArrayList<>();
        toBeSeen = new ArrayList<>();
        alreadySeen = new ArrayList<>();

        this.monster = (Monster)monster;
        Position hero = game.getHero().getPosition();
        Objects.requireNonNull(hero, "Il n'y a pas de héros que le monstre peut suivre.");

        AStarTile start = init();

        toBeSeen.add(start);

        /* Tant qu'il reste des cases à regarder */
        while(toBeSeen.size() > 0) {

            // On retire la plus petite des cases et on va la traiter
            AStarTile tile = getTileWithMinF();
            toBeSeen.remove(tile);

            /* Si c'est la case de l'arrivée, on retourne la solution */
            if (isGoal(tile.getPosition(), hero)){
                return getSolution(hero);
            }

            /* Sinon, pour tous les voisins de notre case : */
            for (AStarTile neighbour : tile){
                int h = neighbour.getDistanceTo(hero);
                int g = tile.getG() + neighbour.getCost();
                int f = g + h;

                if ( f < neighbour.getF()) {
                    neighbour.setParent(tile);
                    neighbour.setG(g);
                    neighbour.setF(f);

                    if (!toBeSeen.contains(neighbour)) toBeSeen.add(neighbour);
                }

            }

            alreadySeen.add(tile);
        }

        /* Maintenant que toutes les cases ont un parent, on reconstruit le chemin. */
        return getSolution(hero);
    }

    /**
     * @param goal la case qu'on veut atteindre le plus rapidement possible
     * @return la direction où aller pour atteindre le plus rapidement le goal à partir de la position du monstre
     */
    private Direction getSolution(Position goal){
        AStarTile tile = getFirstTileOfPathTo(goal);
        return getDirectionToGo(tile.getPosition());
    }

    /**
     * @param position position initiale
     * @param goal position d'arrivée
     * @return vrai si la position initiale est à la même position que celle d'arrivée
     */
    private boolean isGoal(Position position, Position goal){
        return position.equals(goal);
    }

    /**
     * Retourne la case où le monstre devrait aller pour atteindre le héros le plus
     * rapidement possible.
     *
     * @param goal la case d'arrivée
     * @return la case où le monstre doit se diriger
     */
    private AStarTile getFirstTileOfPathTo(Position goal){
        List<AStarTile> path = new ArrayList<>();
        AStarTile tile = null;

        /* On récupère la case qui est à la position d'arrivée */
        for (AStarTile t : allTiles){
            if (t.getPosition().equals(goal)){
                tile = t;
                break;
            }
        }

        /* Si pas de héros, petit problème */
        Objects.requireNonNull(tile, "La case d'arrivée n'a pas été trouvée.");

        /* On ajoute la case d'arrivée dans le chemin */
        path.add(tile);

        /* On construit le chemin arrivée -> départ à l'aide des parents des cases */
        Position start = monster.getPosition();
        while (!tile.getPosition().equals(start)){
            tile = tile.getParent();
            if (tile==null) return new AStarTile(
                    monster.getPosition(),
                    maze.whatIsIn(monster.getPosition()),
                    monster
            );
            path.add(tile);
        }

        /* On renverse le chemin pour qu'il soit dans l'ordre de traversée du monstre */
        Collections.reverse(path);

        /* Si le monstre est juste à côté du héros, on lui donne directement sa case, sinon
        * on prend dans le chemin construit. */
        return path.size() < 2 ? tile : path.get(1);
    }

    /**
     * A partir de la position du monstre, donne la direction où aller pour rejoindre
     * la position demandée.
     *
     * @param goal la position ADJACENTE où aller
     * @return une direction indiquant la position où aller
     */
    private Direction getDirectionToGo(Position goal){
        Position currentPosition = monster.getPosition();
        Direction directionToGo ;
        if (currentPosition.getPositionToDown().equals(goal)){
            directionToGo = Direction.DOWN;
        }else if (currentPosition.getPositionToUp().equals(goal)){
            directionToGo = Direction.UP;
        }else if (currentPosition.getPositionToLeft().equals(goal)){
            directionToGo = Direction.LEFT;
        }else if (currentPosition.getPositionToRight().equals(goal)){
            directionToGo = Direction.RIGHT;
            /* Si la position donnée en but n'est pas une position adjacente à celle du monstre */
        }else {
            directionToGo = Direction.IDLE;
        }
        return directionToGo;
    }

    /**
     * Cherche dans la liste des cases à regarder celle avec la plus petite heuristique
     * @return la case dans ToBeSeen avec la plus petite valeur d'heuristique
     */
    private AStarTile getTileWithMinF(){
        AStarTile minTile = null ;
        int min = Integer.MAX_VALUE;
        for (AStarTile tile : toBeSeen){
            if (tile.getF() < min){
                min = tile.getF();
                minTile = tile;
            }
        }
        return minTile ;
    }

    /**
     * Crée toutes les AStarTiles, leur donne leurs voisins et initialise la case de départ avec les
     * bonnes valeurs pour commencer A*
     * @return la case de départ (celle du monstre) bien formatée
     */
    private AStarTile init(){
        /* On crée tous les AStarTile */
        for (int i = 0; i < maze.getWidth(); i++){
            for (int j =0 ; j < maze.getHeight() ; j++){
                Position position = new Position(i,j);

                allTiles.add(new AStarTile(
                        new Position(position),
                        maze.whatIsIn(position),
                        monster
                ));
            }
        }

        for (AStarTile aTile : allTiles){
            setNeighbours(aTile);
        }

        /* Seule la tile de départ a une évaluation de 0 */
        for (AStarTile tile : allTiles){
            if (tile.getPosition().equals(monster.getPosition())){
                tile.setG(0);
                tile.setF(0);
                return tile;
            }
        }
        return null;
    }

    /**
     * Donne ses voisins à la case sous forme de AStarTile
     * @param tile case à qui on veut donner les voisins
     */
    private void setNeighbours(AStarTile tile){
        Iterator<Position> iterator = tile.iteratorOfPositions();
        while (iterator.hasNext()){
            Position positionOfNeighbour = iterator.next();

            for (AStarTile aTile : allTiles){
                boolean isANeighbour = aTile.getPosition().equals(positionOfNeighbour);

                if (isANeighbour){
                    tile.addNeighbour(aTile);
                    break;
                }

            }
        }
    }


}
