package com.dametdamet.app.model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.dametdamet.app.engine.GamePainter;
import com.dametdamet.app.model.maze.Maze;

/**
 * @author Horatiu Cirstea, Vincent Thomas
 *
 * afficheur graphique pour le game
 * 
 */
public class PacmanPainter implements GamePainter {

	PacmanGame pacmanGame;
	/**
	 * la taille des cases
	 */
	protected static final int WIDTH = 200;
	protected static final int HEIGHT = 200;

	/**
	 * appelle constructeur parent
	 * 
	 * @param game
	 *            le jeutest a afficher
	 */
	public PacmanPainter(PacmanGame game) {
		this.pacmanGame = game;
	}

	/**
	 * methode  redefinie de Afficheur retourne une image du jeu
	 */
	@Override
	public void draw(BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		drawMaze(im);
		drawHero(im);
		drawMonsters(im);
	}

	/**
	 * dessine les monstres sur l'image en paramètre
	 * @param im image sur laquelle on dessine les monstres
	 */
	private void drawMonsters(BufferedImage im){
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		for (Entity monster: pacmanGame) {
			Position position = monster.getPosition();
			crayon.setColor(ColorFactory.INSTANCE.getEntityColor(monster.getType()));
			crayon.fillOval(position.getX()*(WIDTH/ Maze.WIDTH), position.getY()*(HEIGHT/Maze.HEIGHT), WIDTH/Maze.WIDTH, HEIGHT/Maze.HEIGHT);
		}
	}

	/**
	 * dessine le labyrinthe sur l'image en paramètre
	 * @param im image sur laquelle on dessine le labyrinthe
	 */
	private void drawMaze(BufferedImage im){
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		Maze maze = pacmanGame.getMaze();
		Position position = new Position(0,0);
		for(int i = 0; i < Maze.WIDTH; i++){
			for(int j = 0; j < Maze.HEIGHT;j++){
				position.setX(i);
				position.setY(j);
				crayon.setColor(ColorFactory.INSTANCE.getCaseColor(maze.whatIsIn(position).getType()));
				crayon.fillRect(position.getX()*(WIDTH/Maze.WIDTH),position.getY()*(HEIGHT/Maze.HEIGHT),WIDTH/Maze.WIDTH,HEIGHT/Maze.HEIGHT);
			}
		}
	}

	/**
	 * dessine le héros sur l'image en paramètre
	 * @param im image sur laquelle on dessine le héros
	 */
	private void drawHero(BufferedImage im){
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		Entity hero = pacmanGame.getHero();

		Position position = hero.getPosition();
		crayon.setColor(ColorFactory.INSTANCE.getEntityColor((hero.getType())));
		crayon.fillOval(position.getX()*(WIDTH/Maze.WIDTH),position.getY()*(HEIGHT/Maze.HEIGHT),WIDTH/Maze.WIDTH,HEIGHT/Maze.HEIGHT);
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

}
