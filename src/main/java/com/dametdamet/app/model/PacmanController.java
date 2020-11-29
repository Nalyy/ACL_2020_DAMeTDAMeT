package com.dametdamet.app.model;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.dametdamet.app.engine.Command;
import com.dametdamet.app.engine.GameController;

import static java.awt.event.KeyEvent.*;


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
	private List<Command> commandesEnCours;
	
	/**
	 * construction du controleur par defaut le controleur n'a pas de commande
	 */
	public PacmanController() {
		commandesEnCours = new ArrayList<>();
		commandesEnCours.add(Command.IDLE);
	}

	/**
	 * quand on demande les commandes, le controleur retourne la commande en
	 * cours
	 * 
	 * @return commande faite par le joueur
	 */
	public Command getCommand() {
		return commandesEnCours.get(commandesEnCours.size()-1);
	}

	@Override
	/**
	 * met a jour les commandes en fonctions des touches appuyees
	 */
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
			/* DÉPLACEMENTS PERSONNAGE */
		// si on appuie sur 'q', commande joueur est gauche
			case 'q':
			case 'Q':
				if(!commandesEnCours.contains(Command.LEFT))
				commandesEnCours.add(Command.LEFT);
				break;
		// si on appuie sur 'd', commande joueur est droite
			case 'd':
			case 'D':
				if(!commandesEnCours.contains(Command.RIGHT))
					commandesEnCours.add(Command.RIGHT);
				break;

		// si on appuie sur 's', commande joueur est bas
			case 's' :
			case 'S':
				if(!commandesEnCours.contains(Command.DOWN))
					commandesEnCours.add(Command.DOWN);
				break;

		// si on appuie sur 'z', commande joueur est haut
			case 'z':
			case 'Z':
				if(!commandesEnCours.contains(Command.UP))
					commandesEnCours.add(Command.UP);
				break;

			/* ATTAQUES DU JOUEUR */
		// si on appuie sur 'j' ou la flèche directionnelle de gauche, la direction de l'attaque sera gauche
			case VK_LEFT:
			case VK_J:
				if(!commandesEnCours.contains(Command.ATTACK_LEFT))
					commandesEnCours.add(Command.ATTACK_LEFT);
				break;

		// si on appuie sur 'l' ou la flèche directionnelle de droite, la direction de l'attaque sera droite
			case VK_RIGHT:
			case VK_L:
				if(!commandesEnCours.contains(Command.ATTACK_RIGHT))
					commandesEnCours.add(Command.ATTACK_RIGHT);
				break;

		// si on appuie sur 'k' ou la flèche directionnelle du bas, la direction de l'attaque sera bas
			case VK_DOWN:
			case VK_K:
				if(!commandesEnCours.contains(Command.ATTACK_DOWN))
					commandesEnCours.add(Command.ATTACK_DOWN);
				break;

		// si on appuie sur 'i' ou la flèche directionnelle du haut, la direction de l'attaque sera haut
			case VK_UP:
			case VK_I:
				if(!commandesEnCours.contains(Command.ATTACK_UP))
					commandesEnCours.add(Command.ATTACK_UP);
				break;

				/* OPTIONS ÉTAT DU JEU */
		// si on appuie sur 'r', le jeu recommence
			case 'r':
			case 'R':
				if(!commandesEnCours.contains(Command.RETRY))
					commandesEnCours.add(Command.RETRY);
				break;

		// si on appuie sur 'p', le jeu est en pause
			case 'p':
			case 'P':
				if(!commandesEnCours.contains(Command.PAUSE))
					commandesEnCours.add(Command.PAUSE);
				break;

		// si on appuie sur 'c', le jeu se ferme
			case 'c':
			case 'C':
				if(!commandesEnCours.contains(Command.CLOSE))
					commandesEnCours.add(Command.CLOSE);
				break;
		}

	}

	@Override
	/**
	 * met a jour les commandes quand le joueur relache une touche
	 */
	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {
			/* DÉPLACEMENTS PERSONNAGE */
			// si on appuie sur 'q', commande joueur est gauche
			case 'q':
			case 'Q':
				commandesEnCours.remove(Command.LEFT);
				break;
			// si on appuie sur 'd', commande joueur est droite
			case 'd':
			case 'D':
				commandesEnCours.remove(Command.RIGHT);
				break;

			// si on appuie sur 's', commande joueur est bas
			case 's' :
			case 'S':
				commandesEnCours.remove(Command.DOWN);
				break;

			// si on appuie sur 'z', commande joueur est haut
			case 'z':
			case 'Z':
				commandesEnCours.remove(Command.UP);
				break;

			/* ATTAQUES DU JOUEUR */
			// si on appuie sur 'j' ou la flèche directionnelle de gauche, la direction de l'attaque sera gauche
			case VK_LEFT:
			case VK_J:
				commandesEnCours.remove(Command.ATTACK_LEFT);
				break;

			// si on appuie sur 'l' ou la flèche directionnelle de droite, la direction de l'attaque sera droite
			case VK_RIGHT:
			case VK_L:
				commandesEnCours.remove(Command.ATTACK_RIGHT);
				break;

			// si on appuie sur 'k' ou la flèche directionnelle du bas, la direction de l'attaque sera bas
			case VK_DOWN:
			case VK_K:
				commandesEnCours.remove(Command.ATTACK_DOWN);
				break;

			// si on appuie sur 'i' ou la flèche directionnelle du haut, la direction de l'attaque sera haut
			case VK_UP:
			case VK_I:
				commandesEnCours.remove(Command.ATTACK_UP);
				break;

			/* OPTIONS ÉTAT DU JEU */
			// si on appuie sur 'r', le jeu recommence
			case 'r':
			case 'R':
				commandesEnCours.remove(Command.RETRY);
				break;

			// si on appuie sur 'p', le jeu est en pause
			case 'p':
			case 'P':
				commandesEnCours.remove(Command.PAUSE);
				break;

			// si on appuie sur 'c', le jeu se ferme
			case 'c':
			case 'C':
				commandesEnCours.remove(Command.CLOSE);
				break;
		}
	}

	@Override
	/**
	 * ne fait rien
	 */
	public void keyTyped(KeyEvent e) {

	}

}
