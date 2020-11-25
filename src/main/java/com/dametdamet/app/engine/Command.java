package com.dametdamet.app.engine;

/**
 * @author Horatiu Cirstea
 *
 */
public enum Command {
	// Déplacements du personnage
	 LEFT,RIGHT,UP,DOWN,IDLE,
	// Attaques du joueur
	ATTACK_LEFT, ATTACK_RIGHT, ATTACK_UP, ATTACK_DOWN,
	// Options sur l'état du jeu
	PAUSE, RETRY, CLOSE
}
