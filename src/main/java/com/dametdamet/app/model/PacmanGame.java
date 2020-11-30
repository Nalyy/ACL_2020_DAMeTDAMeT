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
import com.dametdamet.app.model.entity.monster.MoveStrategy;
import com.dametdamet.app.model.entity.monster.RandomMove;
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
	private Collection<Entity> monsters;
	private Collection<Entity> projectiles;
	private Maze maze;
	private Timer gameTimer;
	private Timer projectileTimer;
	private int score;
	private int currentLevel;

	private String fileName;
	private String[] filesNames;
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
		hero = new Hero(new Position(maze.getInitialPositionPlayer()),3);
		gameTimer = new Timer();
		gameTimer.pause();

		/* Toutes les stratégies doivent être initalisées avec le jeu */
		RandomMove.INSTANCE.setGame(this);
		RunnerMove.INSTANCE.setGame(this);
		AStarMove.INSTANCE.setGame(this);

		// Re-création du monde
		monsters.clear();
		addEnnemies();
		projectiles.clear();
		ProjectileMove.INSTANCE.setMaze(this.maze);
		projectileTimer = new Timer();


		// Lancement du timer
		gameTimer.top(TIMER_TIME * 1000);

		// Le jeu peut se relancer
		state = GameState.ONGOING;
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
			initMazeOfStrategies();
			// On s'occupe des monstres et des héros
			hero.moveTo(new Position(maze.getInitialPositionPlayer()));
			monsters.clear();
			addEnnemies();
			projectiles.clear();
			ProjectileMove.INSTANCE.setMaze(this.maze);
			projectileTimer = new Timer();
		}
	}

	private void loadMaze(){
		if(fileName != null && !fileName.equals(""))
			maze = AbstractDAOFactory.getAbstractDAOFactory(AbstractDAOFactory.TXT).getMazeDAO().load(fileName);
		else
			maze = new Maze();
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

	/**
	 * Crée et ajoute les monstres au jeu.
	 */
	private void addEnnemies(){
		RandomMove.INSTANCE.setMaze(maze);
		addMonsters();
		addGhosts();

	}

	private void addMonsters(){
		Iterator<Position> initialPositionMonster = maze.getIteratorMonsterPositions();

		// Création des monstres à mettre dans la liste
		while (initialPositionMonster.hasNext()){
			// On met le nouveau monstre dans la liste en lui assignant une position initiale
			monsters.add(new Monster(new Position(initialPositionMonster.next()), RandomMove.INSTANCE));
		}
	}

	/**
	 * Initialise toutes les stratégies avec le labyrinthe actuel.
	 */
	private void initMazeOfStrategies(){
		RandomMove.INSTANCE.setMaze(maze);
		AStarMove.INSTANCE.setMaze(maze);
		RunnerMove.INSTANCE.setMaze(maze);
	}

	private void addGhosts() {
		Iterator<Position> initialPositionsGhosts = maze.getIteratorGhostPositions();
		// Création des fantômes à mettre dans la liste
		while (initialPositionsGhosts.hasNext()){
			// On met le nouveau fantôme dans la liste en lui assignant une position initiale
			monsters.add(new Monster(new Position(initialPositionsGhosts.next()), RandomMove.INSTANCE, EntityType.GHOST));
		}
	}


	public void healHero(int amount){
		hero.gainHP(amount);
	}

	public void hurtHero(int amount) {
		hero.loseHP(amount);
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
			if (projectileTimer.isFinished()) {
				Direction directionAttack = getDirFromAttackCommand(command);
				addProjectile(directionAttack);
				hero.setDirection(directionAttack);
			}
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
			}else {
				state = GameState.PAUSED;
				gameTimer.pause();
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
			if(!conflictWithEntity(targetPosition)){
				monster.moveTo(targetPosition);

				if (!nextDirection.equals(Direction.IDLE)) {
					maze.whatIsIn(monster.getPosition()).applyEffect(this, monster);
				}
				// Si il n'a pas pu se déplacer, on reset sa direction
			}else{
				monster.setDirection(Direction.IDLE);
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
						toRemove = true;
						projToRemove.add(projectile);
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
							projToRemove.add(projectile);
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

	private boolean conflictWithEntity(Position positionToGo){
		boolean conflict = false;

		Iterator<Entity> iterator = this.getMonstersIterator();

		/* On regarde si un monstre se trouve par ici */
		while (iterator.hasNext()) {
			Entity monster = iterator.next();

			if (monster.getPosition().equals(positionToGo)){
				conflict = true;
				break;
			}
		}

		/* Si aucun soucis avec les monstres, on regarde si soucis avec le héros */
		if (!conflict){
			conflict = positionToGo.equals(hero.getPosition());
		}

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
	 * Indiquer que le jeu est gagné.
	 */
	private void setWon() { state = GameState.WON; }

	/**
	 * verifier si le jeu est fini
	 */
	@Override
	public boolean isFinished() {
		return state == GameState.LOST;
	}

	/**
	 * Vérifier si le jeu est gagné.
	 */
	public boolean isWon() { return state == GameState.WON ; }

	/**
	 * Vérifie si le jeu est en pause.
	 * @return vrai si le jeu est en pause
	 */
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
	 *
	 * @return le score du jeu
	 */
	public int getScore() {
		return score;
	}

	public Leaderboard getLeaderboard(){
		return leaderboard;
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
	public Iterator<Entity> getMonstersIterator() { return monsters.iterator();
	}

	/**
	 * @return l'itérateur sur les projectiles
	 */
	public Iterator<Entity> getProjectilesIterator() {
		return projectiles.iterator();
	}

	public void addScore(int scoreToAdd){
		score += scoreToAdd;
	}

	public void addTime(int amountTimeInMs) {
		gameTimer.top(gameTimer.getTime() + amountTimeInMs);
	}

	public void spawnNewChest() {
		maze.addNewChest();
	}

	public void spawnNewMonster(){
		maze.addNewMonster(this);
	}

	public void addMonster(Position position){
		Monster monster = new Monster(position, RandomMove.INSTANCE);
		monsters.add(monster);
	}

	private void addProjectile(Direction direction) {
		Position posHero = hero.getPosition();
		Position position = new Position(posHero);
		Projectile projectile = new Projectile(position, direction, ProjectileMove.INSTANCE);
		projectiles.add(projectile);
		projectileTimer.top(750);
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

	public void hurtEntity(Entity entity,int hpAmount){
		entity.loseHP(hpAmount);
	}

	public void healEntity(Entity entity,int hpAmount){
		entity.gainHP(hpAmount);
	}

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
}
