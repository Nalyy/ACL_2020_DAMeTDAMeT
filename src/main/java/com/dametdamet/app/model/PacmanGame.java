package com.dametdamet.app.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.dametdamet.app.engine.Command;
import com.dametdamet.app.engine.Game;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 *         Version avec personnage qui peut se deplacer. A completer dans les
 *         versions suivantes.
 * 
 */
public class PacmanGame implements Game {
	private boolean isFinished;
	private Hero hero;
	private Collection<Monster> monsters;
	private Maze maze;

	/**
	 * constructeur avec fichier source pour le help
	 * 
	 */
	public PacmanGame(String source) {
		/* Construction du jeu */
		hero = new Hero();
		maze = new Maze();

		// TODO : quel type de liste ?
		monsters = new ArrayList(4);

		// Construction des monstres


		/* Fichier d'aide */
		BufferedReader helpReader;
		try {
			helpReader = new BufferedReader(new FileReader(source));
			String ligne;
			while ((ligne = helpReader.readLine()) != null) {
				System.out.println(ligne);
			}
			helpReader.close();
		} catch (IOException e) {
			System.out.println("Help not available");
		}
	}

	/**
	 * faire evoluer le jeu suite a une commande
	 * 
	 * @param command
	 */
	@Override
	public void evolve(Command command) {
		System.out.println("Execute "+ command);
	}

	/**
	 * Donne la nouvelle position si exécution de cmd.
	 *
	 * @param position
	 * @param command
	 * @return
	 */
	private Position getTargetPosition(Position position, Command command){
		return new Position(0,0);
	}

	/**
	 * indiquer si le jeu est fini
	 */
	private void setFinished(boolean b){
		isFinished = b;
	}

	/**
	 * verifier si le jeu est fini
	 */
	@Override
	public boolean isFinished() {
		// le jeu n'est jamais fini
		return isFinished;
	}

}
