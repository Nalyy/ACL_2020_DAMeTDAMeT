package com.dametdamet.app.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import com.dametdamet.app.engine.Command;
import com.dametdamet.app.engine.Game;
import com.dametdamet.app.model.dao.factory.AbstractDAOFactory;
import com.dametdamet.app.model.maze.Maze;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 *         Version avec personnage qui peut se deplacer. A completer dans les
 *         versions suivantes.
 * 
 */
public class PacmanGame implements Game, Iterable<Entity> {
	private boolean isFinished;
	private Entity hero;
	// TODO : je sais que c'est bien d'utiliser la classe abstraite, mais je sais pas si
	// là c'est vraiment nécessaire... + je commence à me demander si Monster devrait vraiment hériter
	// de Entity
	private Collection<Entity> monsters;
	private final Maze maze;

	/**
	 * constructeur avec fichier source pour le help
	 */
	public PacmanGame(String source, String sourceMaze) {
		/* Construction du jeu */
		Position initialPosition = new Position(0,0);
		hero = new Hero(initialPosition);
		if(sourceMaze != null && !sourceMaze.equals(""))
			maze = AbstractDAOFactory.getAbstractDAOFactory(AbstractDAOFactory.TXT).getFileDAO().load(sourceMaze);
		else
			maze = new Maze();

		// TODO : quel type de liste ?
		// Création des monstres
		monsters = new ArrayList<>();
		addMonsters();

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

	private void addMonsters(){
		MoveStrategy moveStrategy = RandomMove.INSTANCE;
		RandomMove.INSTANCE.setMaze(maze);

		Iterator<Position> initialPositionMonster = maze.getIteratorMonsterPositions();

		// Création des monstres à mettre dans la liste
		while (initialPositionMonster.hasNext()){
			// On met le nouveau monstre dans la liste en lui assignant une position initiale
			monsters.add(new Monster(initialPositionMonster.next(), moveStrategy));
		}
	}

	/**
	 * faire evoluer le jeu suite a une commande
	 * 
	 * @param command la commande courante
	 */
	@Override
	public void evolve(Command command) {

		// Héros
		if (command != Command.IDLE) {
			Position initPosHero = hero.getPosition();
			Position targetPosHero = getTargetPosition(initPosHero, command);

			if (maze.isNotWall(targetPosHero)) {
				hero.moveTo(targetPosHero);
			}
		}

		Position initialPosition;
		Position targetPosition;
		Command nextCommand;
		Position heroPosition = hero.getPosition();

		// Monstres
		for (Entity m : monsters){
			Monster monster = (Monster) m;

			// Déplacement du monstre
			initialPosition = monster.getPosition();
			nextCommand = monster.getNextCommand();
			targetPosition = getTargetPosition(initialPosition, nextCommand);

			// La MoveStrategy du monstre s'assure que le monstre peut bouger à cette case
			monster.moveTo(targetPosition);

			// Test collision avec le héro
			if (targetPosition.equals(heroPosition)) {
				setFinished(true);
			}
		}
	}

	/**
	 * Donne la nouvelle position si exécution de cmd.
	 *
	 * @param position la position de départ
	 * @param command la commande à exécuter
	 * @return une nouvelle position modifiée selon command
	 */
	private Position getTargetPosition(Position position, Command command){
		int x = position.getX();
		int y = position.getY();
		switch (command){
			case UP:
				y = y - 1;
				break;
			case DOWN:
				y = y + 1;
				break;
			case LEFT:
				x = x - 1;
				break;
			case RIGHT:
				x = x + 1;
				break;
		}
		return new Position(x, y);
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
		return isFinished;
	}

	public Maze getMaze() {
		return maze;
	}

	public Entity getHero() {
		return hero;
	}

	@Override
	public Iterator<Entity> iterator() {
		return monsters.iterator();
	}
}
