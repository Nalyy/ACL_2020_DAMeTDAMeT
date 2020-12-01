package com.dametdamet.app.model.entity;

import com.dametdamet.app.model.maze.TileType;

import java.util.*;

// Avec le type d'une Entity, on peut avoir :
//      - les cases sur lesquelles elle ne peut pas marcher
//      - les cases dont elle active les effets
public enum EntityType {
    HERO(new ArrayList<>(Arrays.asList(TileType.WALL, TileType.OUTOFBOUND)),
         new ArrayList<>(Arrays.asList( TileType.STAIRS,
                                        TileType.BONUS,
                                        TileType.HEAL,
                                        TileType.DAMAGE,
                                        TileType.SPAWNER_CHEST,
                                        TileType.SPAWNER_MONSTERS,
                                        TileType.TELEPORTATION,
                                        TileType.TIME,
                                        TileType.DYNAMITE
                 )
        )),

    MONSTER(new ArrayList<>(Arrays.asList(TileType.WALL, TileType.OUTOFBOUND)),
            new ArrayList<>(Arrays.asList(
                    TileType.TELEPORTATION,
                    TileType.DYNAMITE
            )
            )
    ),

    RUNNER(new ArrayList<>(Arrays.asList(TileType.WALL, TileType.OUTOFBOUND)),
            new ArrayList<>(Arrays.asList(
                    TileType.TELEPORTATION,
                    TileType.DYNAMITE
            )
            )
    ),

    PROJECTILE(new ArrayList<>(Arrays.asList(TileType.WALL, TileType.OUTOFBOUND)),
            new ArrayList<>(Arrays.asList()
            )
    ),

    GHOST(new ArrayList<>(Collections.singletonList(TileType.OUTOFBOUND)),
            new ArrayList<>()
    )
    ;

    private final Collection<TileType> cantGoTo;
    private final Collection<TileType> canTrigger;


    EntityType(Collection<TileType> cantGoTo, Collection<TileType> canTrigger) {
        this.cantGoTo = cantGoTo;
        this.canTrigger = canTrigger;
    }

    public Collection<TileType> getLimitations() {
        return cantGoTo;
    }

    public Collection<TileType> getTriggers(){
        return canTrigger;
    }

}
