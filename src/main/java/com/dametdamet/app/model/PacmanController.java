package com.dametdamet.app.model;

import java.awt.event.KeyEvent;

import com.dametdamet.app.engine.Command;
import com.dametdamet.app.engine.GameController;


/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * controleur de type KeyListener
 * 
 */
public class PacmanController implements GameController {

	/**
	 * commande en cours
	 */
	private Command commandEnCours;
	
	/**
	 * construction du controleur par defaut le controleur n'a pas de commande
	 */
	public PacmanController() {
		this.commandEnCours = Command.IDLE;
	}

	/**
	 * quand on demande les commandes, le controleur retourne la commande en
	 * cours
	 * 
	 * @return commande faite par le joueur
	 */
	public Command getCommand() {
		return this.commandEnCours;
	}

	@Override
	/**
	 * met a jour les commandes en fonctions des touches appuyees
	 */
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyChar()) {
		// si on appuie sur 'q', commande joueur est gauche
			case 'q':
			case 'Q':
				this.commandEnCours = Command.LEFT;
				break;
		// si on appuie sur 'd', commande joueur est droite
			case 'd':
			case 'D':
				this.commandEnCours = Command.RIGHT;
				break;

		// si on appuie sur 's', commande joueur est bas
			case 's' :
			case 'S':
				this.commandEnCours = Command.DOWN;
				break;

		// si on appuie sur 'z', commande joueur est haut
			case 'z':
			case 'Z':
				this.commandEnCours = Command.UP;
				break;
		}

	}

	@Override
	/**
	 * met a jour les commandes quand le joueur relache une touche
	 */
	public void keyReleased(KeyEvent e) {
		this.commandEnCours = Command.IDLE;
	}

	@Override
	/**
	 * ne fait rien
	 */
	public void keyTyped(KeyEvent e) {

	}

}
