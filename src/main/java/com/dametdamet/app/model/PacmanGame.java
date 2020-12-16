package com.dametdamet.app.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.dametdamet.app.engine.Command;
import com.dametdamet.app.engine.Game;

import com.dametdamet.app.model.dao.factory.AbstractDAOFactory;
import com.dametdamet.app.model.entity.*;
import com.dametdamet.app.model.entity.attack.Projectile;
import com.dametdamet.app.model.entity.attack.ProjectileMove;
import com.dametdamet.app.model.entity.monster.Monster;
import com.dametdamet.app.model.entity.monster.RandomMove;
import com.dametdamet.app.model.graphic.ExplosionEffect;
import com.dametdamet.app.model.graphic.GraphicalEffect;
import com.dametdamet.app.model.leaderboard.Leaderboard;
import com.dametdamet.app.model.leaderboard.Score;
import com.dametdamet.app.model.entity.monster.*;
import com.dametdamet.app.model.maze.Maze;
import com.dametdamet.app.model.maze.Tile;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 *         Version avec personnage qui peut se deplacer. A completer dans les
 *         versions suivantes.
 * 
 */
public class PacmanGame implements Game {
	private GameState state;
	private Hero hero;
	private final Collection<Entity> monsters;
	private final Collection<Entity> projectiles;
	private final Collection<GraphicalEffect> graphicalEffects;
	private Maze maze;
	private Timer gameTimer;
	private int score;
	private int currentLevel;

	private String fileName;
	private final String[] filesNames;
	private final int nbMazesToDo;

	private Leaderboard leaderboard;
	private boolean hasUpdatedLeaderboard = false;
	private final String fileNameLeaderboard;

	public static int TIMER_TIME = 60; // Temps du timer en seconde

	public PacmanGame(String source, String[] sourceMaze){
		this(source, "", sourceMaze);
	}

	/**
	 * Constructeur avec fichier source pour le help
	 */
	public PacmanGame(String source, String sourceLeaderboard, String[] sourceMaze) {
		monsters = new ArrayList<>();
		projectiles = new ArrayList<>();
		graphicalEffects = new ArrayList<>();
		filesNames = sourceMaze;
		nbMazesToDo = sourceMaze.length;

		fileNameLeaderboard = sourceLeaderboard;

		loadLeaderboard();

		init();

		/* Fichier d'aide */
		if(!source.equals("no help")) {
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
	}

	public void goToNextLevel(){
		currentLevel++;

		// On ajoute le score de passage de niveau
		addScore(currentLevel * 1000);

		// Si c'était le dernier labyrinthe, on arrête le jeu
		if (currentLevel >= nbMazesToDo){
			setWon();

		}else {
			// On charge le prochain labyrinthe
			fileName = filesNames[currentLevel];

			// On change notre maze
			loadMaze();

			// On s'occupe des monstres, des héros et des projectiles
			reinitMazeOfStrategies();

			// On s'occupe des monstres et des héros
			hero.moveTo(new Position(maze.getInitialPositionPlayer()));
			monsters.clear();
			initEnemies();
			projectiles.clear();

		}
	}

	/**
	 * Charge le labyrinthe avec le nom donné.
	 */
	private void loadMaze(){
		if(fileName != null && !fileName.equals(""))
			maze = AbstractDAOFactory.getAbstractDAOFactory(AbstractDAOFactory.TXT).getMazeDAO().load(fileName);
		else
			maze = new Maze();
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
		if (isPaused() || isFinished() || isWon() || isClosed()){
			saveLeaderBoard();
			return;
		}

		Direction directionHero;

		if (isAttack(command)) {
			directionHero = getDirectionFromCommand(Command.IDLE);
			Direction directionAttack = getDirFromAttackCommand(command);
			hero.setDirection(directionAttack);
			projectiles.addAll(hero.shoot());

		} else {
			directionHero = getDirectionFromCommand(command);
		}

		// Héros
		moveHero(directionHero);

		// Monstres
		moveMonsters();

		// Projectiles
		moveProjectiles();

		killMonsters();
		killProjectiles();

		//Effets
		updateGraphicalEffects();

		if(hero.getHP() == 0){
			setFinished();
		}

		/*
		Si le timer est fini, alors le jeu est fini
		*/
		if (gameTimer.isFinished()){
			setFinished();
		}
	}

	private void killMonsters() {
		Collection<Entity> monsterToRemove = new ArrayList<>();

		for (Entity e : monsters) {
			if(e.getHP() == 0){
				monsterToRemove.add(e);
			}
		}
		for(Entity e : monsterToRemove){
			destroyMonster(e);
		}
	}

	private void killProjectiles() {
		Collection<Entity> projectilesToRemove = new ArrayList<>();

		for (Entity e : projectiles) {
			if(e.getHP() == 0){
				projectilesToRemove.add(e);
			}
		}
		for(Entity e : projectilesToRemove){
			destroyMonster(e);
		}
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
			/* Rien ne se passe si le jeu est dans un autre état que ONGOING*/
			if (isWon() || isFinished() || isClosed()){
				return;
			}
			if(isPaused()){
				state = GameState.ONGOING;
				gameTimer.continueTimer();
				continueEffects();
				hero.continueInvicibiltyTimer();
			}else {
				state = GameState.PAUSED;
				gameTimer.pause();
				pauseEffects();
				hero.pauseInvicibiltyTimer();
			}
		}
	}

	/**
	 * Vérifie si la commande courante est une attaque.
	 * @param command la commande courante
	 */
	private boolean isAttack(Command command) {
		return (command == Command.ATTACK_LEFT
				|| command == Command.ATTACK_RIGHT
				|| command == Command.ATTACK_DOWN
				|| command == Command.ATTACK_UP);
	}

	public void addScore(int scoreToAdd){
		score += scoreToAdd;
	}

	public void addTime(int amountTimeInMs) {
		gameTimer.top(gameTimer.getTime() + amountTimeInMs);
	}

	private Direction getDirectionFromCommand(Command command){
		Direction direction;
		switch (command){
			case UP:
				direction = Direction.UP;
				break;
			case DOWN:
				direction = Direction.DOWN;
				break;
			case LEFT:
				direction = Direction.LEFT;
				break;
			case RIGHT:
				direction = Direction.RIGHT;
				break;
			case IDLE:
			default:
				direction = Direction.IDLE;
		}
		return direction;
	}

	private Direction getDirFromAttackCommand(Command command) {
		Command newCommand;
		switch (command) {
			case ATTACK_UP:
				newCommand = Command.UP;
				break;
			case ATTACK_DOWN:
				newCommand = Command.DOWN;
				break;
			case ATTACK_LEFT:
				newCommand = Command.LEFT;
				break;
			case ATTACK_RIGHT:
				newCommand = Command.RIGHT;
				break;
			default:
				newCommand = Command.IDLE;
		}
		return getDirectionFromCommand(newCommand);
	}

	private void destroyMonster(Entity entity){
		monsters.remove(entity);
	}

	private void destroyProjectile(Entity entity){
		projectiles.remove(entity);
	}

	/**
	 * inflige des dégâts à l'entity
	 * @param entity entity
	 * @param hpAmount montant de dégât
	 */
	public void hurtEntity(Entity entity,int hpAmount){
		entity.loseHP(hpAmount);
	}

	/**
	 * soigne l'entité
	 * @param entity entité
	 * @param hpAmount montant du soin
	 */
	public void healEntity(Entity entity,int hpAmount){
		entity.gainHP(hpAmount);
	}

	/*
	 **********************************************************************************
	 *
	 *
	 *               				    GETTERS
	 *
	 *
	 **********************************************************************************
	 */


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
	 *
	 * @return le score du jeu
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return le timer du jeu
	 */
	public int getGameTimer() {
		return (int)(gameTimer.getTime() / 1000);
	}

	public Leaderboard getLeaderboard(){
		return leaderboard;
	}

	public Collection<GraphicalEffect> getGraphicalEffects() {
		return graphicalEffects;
	}

	/**
	 * @return l'itérateur sur les monstres
	 */
	public Iterator<Entity> getMonstersIterator() {
		return monsters.iterator();
	}

	/**
	 * @return l'itérateur sur les projectiles
	 */
	public Iterator<Entity> getProjectilesIterator() {
		return projectiles.iterator();
	}

	/*
	 **********************************************************************************
	 *
	 *
	 *               				    MOVES
	 *
	 *
	 **********************************************************************************
	 */

	/**
	 * Déplace le héro selon une direction.
	 */
	private void moveHero(Direction direction) {
		if (direction != Direction.IDLE) {
			hero.setDirection(direction);
			Position initPosHero = hero.getPosition();
			Position targetPosHero = getTargetPosition(initPosHero, direction);

			Tile tile = maze.whatIsIn(targetPosHero);
			if (hero.canGoTo(tile)) {
				hero.moveTo(targetPosHero);
				tile.applyEffect(this, hero);
			}
		}
	}

	/**
	 * Déplace les monstres et vérifie s'il y a collision avec le joueur.
	 */
	private void moveMonsters(){
		Position initialPosition, targetPosition;
		Position heroPosition = hero.getPosition();
		Direction nextDirection;

		// Monstres
		for (Entity m : monsters){
			Monster monster = (Monster) m;

			// Déplacement du monstre
			initialPosition = monster.getPosition();
			nextDirection = monster.getNextDirection();
			targetPosition = getTargetPosition(initialPosition, nextDirection);

			// La MoveStrategy du monstre s'assure que le monstre peut bouger à cette case
			if(!conflictWithEntity(targetPosition)) {
				monster.moveTo(targetPosition);

				if (!nextDirection.equals(Direction.IDLE)) {
					maze.whatIsIn(monster.getPosition()).applyEffect(this, monster);
				}
				// Si il n'a pas pu se déplacer, on reset sa direction
			}

			// Test collision avec le héros
			if (targetPosition.equals(heroPosition)) {
				hero.loseHP(1);
			}
		}

	}

	/**
	 * Déplace les projectiles et vérifie s'il y a collision avec les monstres.
	 * Quand un projectile atteint un monstre, il est détruit et le monstre perd un point de vie.
	 */
	private void moveProjectiles() {
		Direction nextDirection;
		Collection<Entity> projToRemove = new ArrayList<>();
		boolean toRemove = false;

		for (Entity p : projectiles) {
			Projectile projectile = (Projectile) p;

			nextDirection = projectile.getNextDirection();
			if (!nextDirection.equals(Direction.IDLE)) {
				// Collisions avec les monstres
				for (Entity m : monsters) {
					if (projectile.getPosition().equals(m.getPosition())) {
						// si un projectile atteint un monstre, le monstre est tué, le projectile détruit, et le joueur gagne des points
						hurtEntity(m, 1);
						hurtEntity(projectile, 1);
						toRemove = true;
						addScore(100);
					}
				}

				if(!toRemove){
					Position positionProj = getTargetPosition(projectile.getPosition(), nextDirection);
					projectile.moveTo(positionProj);

					// Collisions avec les monstres
					for (Entity m : monsters) {
						if (positionProj.equals(m.getPosition())) {
							// si un projectile atteint un monstre, le monstre est tué, le projectile détruit, et le joueur gagne des points
							hurtEntity(m, 1);
							hurtEntity(projectile, 1);
							addScore(100);
						}
					}
				}
			} else {
				// Si le projectile ne peut plus avancer, on le détruit.
				projToRemove.add(projectile);
			}
		}

		for(Entity p : projToRemove){
			destroyProjectile(p);
		}
	}

	/**
	 * Vérifie si la position où on veut aller ne contient ni le héros, ni de monstres.
	 *
	 * @param positionToGo position où on veut aller
	 * @return vrai s'il y a une collision, faux sinon
	 */
	private boolean conflictWithEntity(Position positionToGo) {
		boolean conflict = false;

		Iterator<Entity> iterator = this.getMonstersIterator();

		/* On regarde si un monstre se trouve par ici */
		while (iterator.hasNext()) {
			Entity monster = iterator.next();

			if (monster.getPosition().equals(positionToGo)) {
				conflict = true;
				break;
			}
		}

		/* Si aucun soucis avec les monstres, on regarde si soucis avec le héros */
		if (!conflict) conflict = positionToGo.equals(hero.getPosition());

		return conflict;

	}

	/**
	 * Donne la nouvelle position si exécution de cmd.
	 *
	 * @param position la position de départ
	 * @param direction la direction à exécuter
	 * @return une nouvelle position modifiée selon command
	 */
	private Position getTargetPosition(Position position, Direction direction){
		int x = position.getX();
		int y = position.getY();
		switch (direction){
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

	/*
	 **********************************************************************************
	 *
	 *
	 *               				  TILES EFFECTS
	 *
	 *
	 **********************************************************************************
	 */

	public void spawnNewChest() {
		maze.addNewChest();
	}


	/**
	 * Fonction appelée lorsqu'on marche sur une case piège qui fait spawn un monstre.
	 * Elle demande au labyrinthe une position possible où le nouveau monstre peut apparaître.
	 * Si la position existe, alors on fait apparaître un nouveau monstre dans le jeu.
	 */
	public void spawnNewMonster(){
		Position position = maze.addNewMonster(this);
		if (position != null){
			Monster monster = new Monster(position, AStarMove.INSTANCE);
			monsters.add(monster);
		}
	}


	/*
	 **********************************************************************************
	 *
	 *
	 *               				   INIT
	 *
	 *
	 **********************************************************************************
	 */


	/**
	 * Initialise le jeu comme neuf.
	 */
	public void init(){
		currentLevel = 0;
		score = 0;

		if (nbMazesToDo > 0){
			fileName = filesNames[0];
		}else {
			fileName = "";
		}

		loadMaze();

		/* Construction du jeu */
		hero = new Hero(new Position(maze.getInitialPositionPlayer()));
		gameTimer = new Timer();
		gameTimer.pause();

		initAllStrategies();

		// Re-création du monde
		monsters.clear();
		graphicalEffects.clear();
		initEnemies();
		projectiles.clear();


		// Lancement du timer
		gameTimer.top(TIMER_TIME * 1000);

		// Le jeu peut se relancer
		state = GameState.ONGOING;
	}

	/**
	 * Initalise les stratégies de mouvement avec le jeu.
	 */
	private void initAllStrategies(){
		RandomMove.INSTANCE.setGame(this);
		RunnerMove.INSTANCE.setGame(this);
		AStarMove.INSTANCE.setGame(this);
		ProjectileMove.INSTANCE.setGame(this);
	}

	/**
	 * Crée et ajoute tous les ennemis au jeu.
	 */
	private void initEnemies(){
		RandomMove.INSTANCE.setMaze(maze);
		initMonsters();
		initGhosts();
		initRunners();
	}

	/**
	 * Crée et ajoute les monstres au jeu.
	 */
	private void initMonsters(){
		Iterator<Position> initialPositionMonster = maze.getIteratorPositions(EntityType.MONSTER);

		// Création des monstres à mettre dans la liste
		while (initialPositionMonster.hasNext()){
			// On met le nouveau monstre dans la liste en lui assignant une position initiale
			monsters.add(new Monster(new Position(initialPositionMonster.next()), RandomMove.INSTANCE));
		}
	}

	/**
	 * Crée et ajoute les fantômes au jeu.
	 */
	private void initGhosts() {
		Iterator<Position> initialPositionsGhosts = maze.getIteratorPositions(EntityType.GHOST);
		// Création des fantômes à mettre dans la liste
		while (initialPositionsGhosts.hasNext()){
			// On met le nouveau fantôme dans la liste en lui assignant une position initiale
			monsters.add(new Monster(new Position(initialPositionsGhosts.next()), RandomMove.INSTANCE, EntityType.GHOST));
		}
	}

	/**
	 * Crée et ajoute les runners au jeu.
	 */
	private void initRunners() {
		Iterator<Position> initialPositionsRunner = maze.getIteratorPositions(EntityType.RUNNER);
		// Création des fantômes à mettre dans la liste
		while (initialPositionsRunner.hasNext()){
			// On met le nouveau fantôme dans la liste en lui assignant une position initiale
			monsters.add(new Monster(new Position(initialPositionsRunner.next()), RunnerMove.INSTANCE, EntityType.RUNNER));
		}
	}

	/**
	 * Ré-initialise toutes les stratégies avec le labyrinthe actuel.
	 * À utiliser lorsqu'on change de labyrinthe (= niveau suivant).
	 */
	private void reinitMazeOfStrategies(){
		RandomMove.INSTANCE.setMaze(maze);
		AStarMove.INSTANCE.setMaze(maze);
		RunnerMove.INSTANCE.setMaze(maze);
		ProjectileMove.INSTANCE.setMaze(this.maze);
	}

	/*
	 **********************************************************************************
	 *
	 *
	 *               				GAME STATES
	 *
	 *
	 **********************************************************************************
	 */

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
	 * Indiquer que le jeu est gagné.
	 */
	private void setWon() { state = GameState.WON; }

	/**
	 * Vérifier si le jeu est gagné.
	 */
	public boolean isWon() { return state == GameState.WON ; }

	/**
	 * Vérifie si le jeu est en pause.
	 * @return vrai si le jeu est en pause
	 */
	@Override
	public boolean isPaused() {
		return state == GameState.PAUSED;
	}

	/**
	 * Vérifie si le jeu est en cours.
	 * @return vrai si le jeu est en cours
	 */
	public boolean isOnGoing(){ return state == GameState.ONGOING;}

	/**
	 * Vérifie si le jeu est fermé.
	 * @return vrai si le jeu est fermé.
	 */
	@Override
	public boolean isClosed(){
		return state == GameState.CLOSED;
	}

	/**
	 * Indique que le joueur a fermé le jeu.
	 */
	private void setClosed(){
		state = GameState.CLOSED;
	}

	/*
	 **********************************************************************************
	 *
	 *
	 *               				LEADERBOARD
	 *
	 *
	 **********************************************************************************
	 */

	/**
	 * Sauvegarde le leaderboard dans un fichier si la partie est terminée.
	 */
	private void saveLeaderBoard(){
		if(isFinished() || isWon() || isClosed()){
			if(!hasUpdatedLeaderboard){
				hasUpdatedLeaderboard = true;
				leaderboard.add(new Score(LocalDate.now().toString(), score));
				if(fileNameLeaderboard != null && !fileNameLeaderboard.equals(""))
					AbstractDAOFactory.getAbstractDAOFactory(AbstractDAOFactory.TXT).getLeaderboardDAO().save(leaderboard, fileNameLeaderboard);
			}
		}
	}


	/**
	 * Charge un Leaderboard en fonction du nom de fichier donné
	 */
	private void loadLeaderboard(){
		if(fileNameLeaderboard != null && !fileNameLeaderboard.equals("")){
			leaderboard = AbstractDAOFactory.getAbstractDAOFactory(AbstractDAOFactory.TXT).getLeaderboardDAO().load(fileNameLeaderboard);
		}else{
			leaderboard = new Leaderboard();
		}
	}

	/*
	**********************************************************************************
	*
	*
	*               				EFFECTS
	*
	*
	**********************************************************************************
	*/
	private void updateGraphicalEffects(){
		Collection<GraphicalEffect> effects_to_destroy = new ArrayList<>();
		for(GraphicalEffect effect:graphicalEffects){
			effect.update();
			if(effect.isFinished()) effects_to_destroy.add(effect);
		}

		for(GraphicalEffect effect: effects_to_destroy){
			destroyEffect(effect);
		}
	}

	private void pauseEffects(){
		for(GraphicalEffect effect:graphicalEffects){
			effect.pauseTimer();
		}
	}

	private void continueEffects(){
		for(GraphicalEffect effect:graphicalEffects){
			effect.continueTimer();
		}
	}

	private void destroyEffect(GraphicalEffect effect){
		graphicalEffects.remove(effect);
	}

	public void addExplosion(Position position){
		graphicalEffects.add(new ExplosionEffect(position));
	}


}
