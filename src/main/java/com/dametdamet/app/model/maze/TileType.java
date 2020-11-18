package com.dametdamet.app.model.maze;

public enum TileType {
    // Type            // symbole dans fichier, description
    EMPTY,             // 0, la case est vide
    WALL,              // 1, la case est un mur
    STAIRS,            // 2, la case est un passage de niveaux
    OUTOFBOUND,        // (vide), la case n'est pas atteignable (hors du labyrinthe)
    HEAL,              // H, case magique : soigne le héros
    TIME,              // T, case magique : ajoute du temps au timer
    SPAWNER_CHEST,     // C, case magique : fait apparaître des coffres
    BONUS,             // B, conséquence de case magique : un coffre bonus peut y apparaître
    DAMAGE,            // D, case piège : blesse le héros
    SPAWNER_MONSTERS,  // S, case piège : fait apparaître un monstre
    TELEPORTATION      // P, case de téléportation : téléporte une entité sur l'autre case du même type
}
