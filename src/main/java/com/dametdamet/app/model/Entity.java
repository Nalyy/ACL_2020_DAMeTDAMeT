package com.dametdamet.app.model;

public abstract class Entity {
    private Position position;

    private TypeEntity type;

    public Entity(Position position,TypeEntity type){
        this.position = position;
        this.type = type;
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

    public TypeEntity getType() {
        return type;
    }
}
