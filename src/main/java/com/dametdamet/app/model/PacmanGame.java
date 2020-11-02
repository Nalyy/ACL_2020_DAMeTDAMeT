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
	private GameState state;
	private Entity hero;
	private Collection<Entity> monsters;
	private Maze maze;
	private Timer gameTimer;

	public static int TEMPS_TIMER = 60; // Temps du timer en seconde

	/**
	 * Constructeur avec fichier source pour le help
	 */
	public PacmanGame(String source, String sourceMaze) {
		monsters = new ArrayList<>();

		/* Construction du Labyrinthe */
		if(sourceMaze != null && !sourceMaze.equals(""))
			maze = AbstractDAOFactory.getAbstractDAOFactory(AbstractDAOFactory.TXT).getFileDAO().load(sourceMaze);
		else
			maze = new Maze();

		init();

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
	 * Initialise le jeu comme neuf.
	 */
	public void init(){
		/* Construction du jeu */
		hero = new Hero(new Position(maze.getInitialPositionPlayer()));
		gameTimer = new Timer();
		gameTimer.pause();

		// Re-création du monde
		monsters.clear();
		addMonsters();

		// Lancement du timer
		gameTimer.top(TEMPS_TIMER * 1000);

		// Le jeu peut se relancer
		state = GameState.ONGOING;
	}

	/**
	 * Ajoute les monstres au jeu.
	 */
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
		boolean optionCommand = (command == Command.CLOSE)
				|| (command == Command.PAUSE)
				|| (command == Command.RETRY);

		if (optionCommand) checkOptions(command);

		/*
		Il ne faut pas que le reste du jeu tourne si on est en pause ou si le jeu est fini, donc on return
		/!\ Il faut bien que cette vérification soit faite APRÈS le check de la commande
		 */
		if (isPaused() || isFinished()){
			return;
		}

		/*
		Si le timer est fini, alors le jeu est fini
		*/
		if (gameTimer.isFinished()){
			setFinished();
		}


		// Héros
		if (command != Command.IDLE) {
			Position initPosHero = hero.getPosition();
			Position targetPosHero = getTargetPosition(initPosHero, command);

			if (maze.isNotWall(targetPosHero)) {
				hero.moveTo(targetPosHero);
			}
		}

		// Monstres
		moveMonsters();


	}

	/**
	 * Exécute une action d'option en fonction de la commande.
	 * @param command commande demandée par l'utilisateur.
	 */
	private void checkOptions(Command command){
		// Le joueur ferme la fenêtre du jeu
		if (command == Command.CLOSE){
			setClosed();
		}

		// Si le joueur veut recommencer la partie, il peut, sauf si le jeu est en pause
		if (command == Command.RETRY){
			if (!isPaused()){
				init();
			}
		}

		// Si le jeu est déjà en pause, on le lance, sinon on met le jeu en pause
		if (command == Command.PAUSE){
			state = isPaused() ? GameState.ONGOING : GameState.PAUSED;
		}
	}

	/**
	 * Déplace les monstres et vérifie s'il y a collision avec le joueur.
	 */
	private void moveMonsters(){
		Position initialPosition, targetPosition;
		Position heroPosition = hero.getPosition();
		Command nextCommand;

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
				setFinished();
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
	 * Indiquer si le jeu est fini
	 */
	private void setFinished(boolean b){
		state = (b ? GameState.LOST : GameState.ONGOING);
	}

	/**
	 * Indiquer que le jeu est fini.
	 */
	private void setFinished(){
		state = GameState.LOST;
	}

	/**
	 * verifier si le jeu est fini
	 */
	@Override
	public boolean isFinished() {
		return state == GameState.LOST;
	}

	/**
	 * Vérifie si le jeu est en pause.
	 * @return vrai si le jeu est en pause
	 */
	public boolean isPaused() {
		return state == GameState.PAUSED;
	}

	/**
	 * Vérifie si le jeu est fermé.
	 * @return vrai si le jeu est fermé.
	 */
	public boolean isClosed(){
		return state == GameState.CLOSED;
	}

	/**
	 * Indique que le joueur a fermé le jeu.
	 */
	private void setClosed(){
		state = GameState.CLOSED;
	}

	/**
	 * @return le labyrinthe du jeu
	 */
	public Maze getMaze() {
		return maze;
	}

	/**
	 * @return le héros du jeu
	 */
	public Entity getHero() {
		return hero;
	}

	/**
	 * @return le timer du jeu
	 */
	public int getGameTimer() {
		return (int)(gameTimer.getTime() / 1000);
	}

	/**
	 * @return l'itérateur sur les monstres
	 */
	@Override
	public Iterator<Entity> iterator() {
		return monsters.iterator();
	}
}
