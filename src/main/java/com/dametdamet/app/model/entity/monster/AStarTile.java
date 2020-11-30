package com.dametdamet.app.model.entity.monster;

import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.maze.Tile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class AStarTile implements Iterable<AStarTile>{
    private Position position;
    private Tile tile;
    private Entity entity;
    private AStarTile parent;
    private int g;
    private int f;
    private int cost; // coût pour s'y rendre : MAX si l'entité ne peut pas s'y déplacer
    private List<AStarTile> neighbours;

    private final int CUSTOM_MAX = 9999;    // voir l'explication dans le constructeur pour
                                            // la non-utilisation de Integer.MAX_VALUE

    /**
     * Construit une case formatée pour faciliter l'implémentation de l'algorithme A*.
     * Une case A* possède donc la case qu'elle représente dans le labyrinthe et sa position,
     * et le monstre qui souhaite aller chercher le héros.
     *
     * @param position la position de la case du labyrinthe qu'elle représente
     * @param tile la case du labyrinthe qu'elle représente
     * @param entity le monstre qui est concerné par la stratégie A*
     */
    public AStarTile(Position position, Tile tile, Entity entity){
        Objects.requireNonNull(tile, "La case donnée est nulle.");
        Objects.requireNonNull(entity, "L'entité donnée est nulle.");
        this.position = position;
        this.tile = tile;
        this.entity = entity;

        this.g = CUSTOM_MAX;    // pas max.integer sinon f overflow lors d'addition et devient négatif
                                // => pb dans la recherche du min avec f
        this.f = CUSTOM_MAX; // cf g, par sécurité
        this.cost = entity.canGoTo(tile) ? 1 : CUSTOM_MAX;

        neighbours = new ArrayList<>(4);
    }

    public int getG(){
        return g;
    }

    public void setG(int value){
        this.g = value;
    }

    public int getF(){
        return f;
    }

    public void setF(int value ){
        this.f = value;
    }

    /**
     * Coût de la case
     * Si l'entité ne peut pas y aller, infini
     *
     * @return 1 ou l'infini
     */
    public int getCost(){
        return cost;
    }

    /**
     * Calcule la distance de la position de cette case jusqu'à la position donnée.
     * @param goal la position à atteindre
     * @return la distance à vol d'oiseau entre la position de cette case et celle donnée.
     */
    public int  getDistanceTo(Position goal){
        int ev =
                (int)
                        Math.sqrt(
                                Math.pow(position.getX() - goal.getX(), 2)
                                        +
                                        Math.pow(position.getY() - goal.getY(), 2)
                        );
        return ev;
    }

    /**
     * Donne un parent à cette case.
     * @param tile le parent de cette case
     */
    public void setParent(AStarTile tile){
        this.parent = tile;
    }


    /**
     * @return le parent de cette case
     */
    public AStarTile getParent(){
        return parent;
    }

    /**
     * Ajoute un voisin à la case.
     * @param tile le voisin à ajouter à cette case
     */
    public void addNeighbour(AStarTile tile){
        neighbours.add(tile);
    }

    private List<Position> getPositionsNextTo(){
        List<Position> neighbours = new ArrayList<>(4);

        neighbours.add(position.getPositionToRight());
        neighbours.add(position.getPositionToLeft());
        neighbours.add(position.getPositionToDown());
        neighbours.add(position.getPositionToUp());

        return neighbours;
    }

    public Position getPosition() {
        return position;
    }

    /**
     * @return l'itérateur sur les positions autour de la position de cette case
     */
    public Iterator<Position> iteratorOfPositions() {
        return getPositionsNextTo().iterator();
    }

    /**
     * @return un itérateur sur les voisins de cette case
     */
    @Override
    public Iterator<AStarTile> iterator() {
        return neighbours.iterator();
    }

    @Override
    public String toString() {
        return "AStarTile{" +
                "position=" + position +
                ", g=" + g +
                ", f=" + f +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AStarTile aStarTile = (AStarTile) o;
        return cost == aStarTile.cost &&
                position.equals(aStarTile.position) &&
                tile.equals(aStarTile.tile) &&
                entity.equals(aStarTile.entity) &&
                neighbours.equals(aStarTile.neighbours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, tile, entity, cost, neighbours);
    }
}
