package com.dametdamet.app.start;

import com.dametdamet.app.model.graphic.PacmanPainter;
import com.dametdamet.app.engine.GameEngineGraphical;
import com.dametdamet.app.model.PacmanController;
import com.dametdamet.app.model.PacmanGame;
import com.dametdamet.app.start.launcher.LauncherController;
import com.dametdamet.app.start.launcher.LauncherModel;
import com.dametdamet.app.start.launcher.LauncherView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * lancement du moteur avec le jeu
 */
public class App {

	public static void main(String[] args) throws InterruptedException {

		//mazes de base
		String[] mazes = new String[2];
		mazes[0] = "maze_1.txt";
		mazes[1] = "maze_2.txt";

		//on initialise les classes pour le launcher
		LauncherModel launcher = new LauncherModel(mazes);
		LauncherController launcherController = new LauncherController(launcher);
		LauncherView view = new LauncherView(launcherController,launcher);

		launcherController.setView(view);
		launcher.addPropertyChangeListener(view);

		//on affiche le launcher
		view.setVisible();

		//tant que le launcher n'est pas fermé
		while (!launcher.isExit()){

		}

		//si le launcher est dans une configuration pour le lancement
		if(launcher.isLaunch()) {

			// creation du jeu particulier et de son afficheur
			PacmanGame game = new PacmanGame("helpFilePacman.txt", "leaderboard.txt", launcher.getFileMazes());
			PacmanPainter painter = new PacmanPainter(game, launcher.getWidth(), launcher.getHeight());
			PacmanController controller = new PacmanController();

			// classe qui lance le moteur de jeu generique
			GameEngineGraphical engine = new GameEngineGraphical(game, painter, controller);
			engine.run();
		}

	}

}
