package com.dametdamet.app.model.maze.trapTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.entity.EntityType;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

public class Dynamite extends Tile {

    private final static int AMOUNT_DMG = 1;
    public final static int EXPLOSION_RANGE = 1;


    private Position position;
    public Dynamite(Position position){
        super(TileType.DYNAMITE);
        this.position = position;

    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if(entity.canTrigger(this) && !isPressed()){
            game.addExplosion(position);
            for (Entity e: game) {
                if(e.isInRadius(position,EXPLOSION_RANGE)) {
                    game.hurtEntity(e, AMOUNT_DMG);
                    if(e.getHP() == 0 && entity.getType() == EntityType.HERO) game.addScore(100);
                }
            }
            if(game.getHero().isInRadius(position,EXPLOSION_RANGE)) game.hurtEntity(game.getHero(),AMOUNT_DMG);
            setPressed(true);
        }
    }
}
