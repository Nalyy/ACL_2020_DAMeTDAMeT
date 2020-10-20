package com.dametdamet.app.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.dametdamet.app.engine.GamePainter;

import javax.imageio.ImageIO;

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

		drawHero(im);
		drawMaze(im);
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
			crayon.fillOval(position.getX()*(WIDTH/Maze.LENGTH), position.getY()*(HEIGHT/Maze.HEIGHT), WIDTH/Maze.LENGTH, HEIGHT/Maze.HEIGHT);
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
		for(int i = 0; i < Maze.LENGTH;i++){
			for(int j = 0; j < Maze.HEIGHT;j++){
				position.setX(i);
				position.setY(j);
				crayon.setColor(ColorFactory.INSTANCE.getCaseColor(maze.whatIsIn(position)));
				crayon.fillRect(position.getX()*(WIDTH/Maze.LENGTH),position.getY()*(HEIGHT/Maze.HEIGHT),WIDTH/Maze.LENGTH,HEIGHT/Maze.HEIGHT);
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
		crayon.fillOval(position.getX()*(WIDTH/Maze.LENGTH),position.getY()*(HEIGHT/Maze.HEIGHT),WIDTH/Maze.LENGTH,HEIGHT/Maze.HEIGHT);
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
