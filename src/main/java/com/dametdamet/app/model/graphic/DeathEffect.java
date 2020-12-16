package com.dametdamet.app.model.graphic;

import com.dametdamet.app.model.Position;

public class DeathEffect extends GraphicalEffect{

    public static final int NUM_SPRITE_MAX = 1;
    private static final int INITIAL_LIFETIME = Integer.MAX_VALUE;
    private static final int TIME_BETWEEN_ANIMATION = Integer.MAX_VALUE;
    private static final int SIZE = 1;

    public DeathEffect(Position position) {
        super(GraphicalEffectType.DEATH,
                new Position(position),
                TIME_BETWEEN_ANIMATION,
                SIZE, SIZE,
                INITIAL_LIFETIME,
                NUM_SPRITE_MAX);
    }
}
