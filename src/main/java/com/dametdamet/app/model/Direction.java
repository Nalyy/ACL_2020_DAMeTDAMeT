package com.dametdamet.app.model;

public enum Direction {
    LEFT,RIGHT,UP,DOWN,IDLE;

    public Direction getOppositeDirection(Direction direction) {
        switch (direction) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                return IDLE;
        }
    }
}
