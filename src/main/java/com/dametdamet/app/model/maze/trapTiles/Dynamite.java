package com.dametdamet.app.model.maze.trapTiles;

import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.model.Position;
import com.dametdamet.app.model.entity.Entity;
import com.dametdamet.app.model.entity.EntityType;
import com.dametdamet.app.model.maze.Tile;
import com.dametdamet.app.model.maze.TileType;

import java.util.Iterator;

public class Dynamite extends Tile {

    private final static int AMOUNT_DMG = 1;
    private final static int EXPLOSION_RANGE = 1;


    private final Position position;
    public Dynamite(Position position){
        super(TileType.DYNAMITE);
        this.position = position;

    }

    @Override
    public void applyEffect(PacmanGame game, Entity entity) {
        if(entity.canTrigger(this) && !isPressed()){

            // On parcourt les monstres pour voir s'ils sont touchés par la dynamite
            Iterator<Entity> iterator = game.getMonstersIterator();
            while (iterator.hasNext()) {
                Entity e = iterator.next();
                if(e.isInRadius(position,EXPLOSION_RANGE)) {
                    game.hurtEntity(e, AMOUNT_DMG);
                    if(e.getHP() == 0 && entity.getType() == EntityType.HERO) game.addScore(100);
                }
            }

            // Puis on regarde si le héros est touché par la dynamite
            if(game.getHero().isInRadius(position,EXPLOSION_RANGE)) game.hurtEntity(game.getHero(),AMOUNT_DMG);
            setPressed(true);
        }
    }
}
