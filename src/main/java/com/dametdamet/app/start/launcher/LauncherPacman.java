package com.dametdamet.app.start.launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LauncherPacman {

    private boolean launch = false;
    private boolean close = false;
    private JFrame frame;

    private JButton exitButton = new JButton("quitter");
    private JButton launchButton = new JButton("lancer");
    private JLabel sizeWindowLabel = new JLabel("Taille de la fenêtre de jeu");
    private JLabel mazeLabel = new JLabel("Labyrinthe(s)");
    private JLabel widthLabel = new JLabel("Largeur  :");
    private JLabel heightLabel = new JLabel("Hauteur :");
    private TextField widthTextField = new TextField();
    private TextField heightTextField = new TextField();

    private List<String> maze;

    public LauncherPacman(){
        frame = new JFrame();

        maze = new ArrayList<>();
        maze.add("mazes/maze_1.txt");

        frame.getContentPane().setLayout(new GridLayout());

        widthTextField.setText("800");
        heightTextField.setText("800");

        buildFrame();
        LaunchExitListener listener = new LaunchExitListener();
        exitButton.addActionListener(listener);
        launchButton.addActionListener(listener);

    }

    private void buildFrame(){
        JPanel panel = (JPanel)frame.getContentPane();

        panel.removeAll();

        panel.add(sizeWindowLabel);
        //Box pour size screen
        Box vboxSize = Box.createVerticalBox();

        //Box pour label et textField largeur
        Box hboxX = Box.createHorizontalBox();
        hboxX.add(widthLabel);
        hboxX.add(widthTextField);

        //Box pour label et textField hauteur
        Box hboxY = Box.createHorizontalBox();
        hboxY.add(heightLabel);
        hboxY.add(heightTextField);

        //Box pour les boutons
        Box hboxButton = Box.createHorizontalBox();

        hboxButton.add(exitButton);
        hboxButton.add(launchButton);



        vboxSize.add(hboxX);
        vboxSize.add(hboxY);
        panel.add(vboxSize);

        panel.add(mazeLabel);
        listToComponent(panel);

        panel.add(hboxButton);

        panel.setLayout(new GridLayout(4 + maze.size(),1));

        frame.pack();

    }

    public void setVisible() {
        frame.setVisible(true);
    }


    public void dispose() {
        frame.dispose();
        close = true;
    }


    private class LaunchExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(actionEvent.getActionCommand().equals("lancer")){
                launch = true;
            }
            dispose();

        }
    }

    private class ButtonMazeListener implements ActionListener {

        private int index;
        public ButtonMazeListener(int index){
           this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(actionEvent.getActionCommand().equals("+")){
                addMaze(index+1, "mazes/maze_1.txt");
            }
            if(actionEvent.getActionCommand().equals("-")){
                removeMaze(index);
            }
            buildFrame();
        }
    }

    public synchronized boolean isClose(){
        return close;
    }

    public boolean isLaunch() {
        return launch;
    }

    private void addMaze(int index,String name){
        maze.add(index,name);
    }

    private void removeMaze(int index){
        maze.remove(index);
    }


    private void listToComponent(JPanel panel){
        for (int i = 0; i < maze.size();i++) {
            String s = maze.get(i);
            Box boxMaze = Box.createHorizontalBox();
            Button plus = new Button("+");
            Button minus = new Button("-");
            if(i == 0) minus.setEnabled(false);
            ///ajout listener aux boutons +et -
            ButtonMazeListener list = new ButtonMazeListener(i);
            plus.addActionListener(list);
            minus.addActionListener(list);

            boxMaze.add(new Label(s));
            boxMaze.add(plus);
            boxMaze.add(minus);
            panel.add(boxMaze);
        }
    }
}
