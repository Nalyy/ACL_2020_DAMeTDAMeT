package com.dametdamet.app.model;

public abstract class Entity {
    private Position position;

    public Entity(Position position){
        this.position = position;
    }

    /**
     * Remplace la position avec la nouvelle position.
     * @param position
     */
    public void moveTo(Position position){
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }


    /**
     * Retourne la position.
     * @return position
     */
    public Position getPosition() {
        return position;
    }
}
