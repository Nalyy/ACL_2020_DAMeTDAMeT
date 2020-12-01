package com.dametdamet.app.engine;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 *         un jeu qui peut evoluer (avant de se terminer) sur un plateau width x
 *         height
 */
public interface Game {

	/**
	 * methode qui contient l'evolution du jeu en fonction de la commande
	 * 
	 * @param userCmd
	 *            commande utilisateur
	 */
    void evolve(Command userCmd);

	/**
	 * Initialise le jeu
	 */
    void init();

	/**
	 * @return true si et seulement si le jeu est fini
	 */
    boolean isFinished();

	/**
	 * @return true si et seulement si le jeu est fermé
	 */
    boolean isClosed();

	/**
	 * @return true si et seulement si le jeu est en pause
	 */
    boolean isPaused();

}
