package com.dametdamet.app.model.graphic;

import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.maze.trapTiles.Dynamite;


public class ExplosionEffect extends GraphicalEffect{

    public static final int NUM_SPRITE_MAX = 71;
    private static final int INITIAL_LIFETIME = 5000;
    private static final int TIME_BETWEEN_ANIMATION = INITIAL_LIFETIME / NUM_SPRITE_MAX;
    private static final int SIZE = (Dynamite.EXPLOSION_RANGE+1)*2 + 1;

    public ExplosionEffect(Position position) {
        super(GraphicalEffectType.EXPLOSION
                , new Position(position.getX() - Dynamite.EXPLOSION_RANGE - 1,position.getY() - Dynamite.EXPLOSION_RANGE - 1)
                , TIME_BETWEEN_ANIMATION
                , SIZE, SIZE
                , INITIAL_LIFETIME
                , NUM_SPRITE_MAX);
    }
}
