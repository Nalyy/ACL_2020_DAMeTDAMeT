package com.dametdamet.app.start.launcher;



import com.dametdamet.app.engine.Command;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class LauncherView implements PropertyChangeListener {

    private LauncherController controller;

    private JFrame frame;
    private JButton exitButton = new JButton("quitter");
    private JButton launchButton = new JButton("lancer");
    private JLabel sizeWindowLabel = new JLabel("Taille de la fenêtre de jeu");
    private JLabel mazeLabel = new JLabel("Labyrinthe(s)");
    private JLabel widthLabel = new JLabel("Largeur  :");
    private JLabel heightLabel = new JLabel("Hauteur :");
    private JTextField widthTextField = new JTextField();
    private JTextField heightTextField = new JTextField();

    public LauncherView(LauncherController controller,LauncherModel launcherModel){
        this.controller = controller;

        frame = new JFrame();

        frame.getContentPane().setLayout(new GridLayout());
        widthTextField.setText("800");
        heightTextField.setText("800");
        widthTextField.getDocument().addDocumentListener(new TextFieldListener(widthTextField));
        heightTextField.getDocument().addDocumentListener(new TextFieldListener(heightTextField));

        buildFrame(launcherModel.getMazes());

        LaunchExitListener listener = new LaunchExitListener();
        exitButton.addActionListener(listener);
        launchButton.addActionListener(listener);

    }

    public void setVisible(){
        frame.setVisible(true);
    }

    private void buildFrame(List<String> mazeList){
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
        mazeListToComponent(panel,mazeList);

        panel.add(hboxButton);

        panel.setLayout(new GridLayout(4 + mazeList.size(),1));

        frame.pack();

    }

    private void mazeListToComponent(JPanel panel, List<String> list){
        for (int i = 0; i < list.size();i++) {
            String s = list.get(i);
            Box boxMaze = Box.createHorizontalBox();
            Button plus = new Button("+");
            Button minus = new Button("-");
            if(i == 0) minus.setEnabled(false);

            ///ajout listener aux boutons +et -
            ButtonMazeListener listener = new ButtonMazeListener(i);
            plus.addActionListener(listener);
            minus.addActionListener(listener);

            List<String> listFile = new ArrayList<>();
            //On utilise une fonction différente si on lance depuis un jar
            if(getClass().getProtectionDomain().getCodeSource().getLocation().toString().endsWith(".jar"))
                listFile = getFileNamesJar();
            else
                listFile = getFileNames();

            JComboBox<String> combox = getComboboxFromFile(listFile,s);
            combox.addActionListener(new ComboboxListener(i,combox));

            boxMaze.add(combox);
            boxMaze.add(plus);
            boxMaze.add(minus);
            panel.add(boxMaze);
        }
    }

    private class LaunchExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(actionEvent.getActionCommand().equals("lancer")){
                controller.notifyLaunch();
            }
            controller.notifyExit();
            frame.dispose();

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
                controller.notifyAddMaze(index+1);

            }
            if(actionEvent.getActionCommand().equals("-")){
                controller.notifyRemoveMaze(index);

            }
        }
    }

    private class ComboboxListener implements ActionListener{

        private int index;
        private JComboBox<String> comboBox;

        public ComboboxListener(int index,JComboBox<String> comboBox){
            this.index = index;
            this.comboBox = comboBox;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.notifyModifyMaze(index,(String)comboBox.getSelectedItem());
        }
    }

    private class TextFieldListener implements DocumentListener {

        private JTextField text;
        public TextFieldListener(JTextField text){
            this.text = text;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            if(!text.getText().equals("") && isNumeric(text.getText())) {
                if (text.equals(heightTextField)) {
                    controller.notifyHeight(Integer.parseInt(text.getText()));
                }
                if (text.equals(widthTextField)) {
                    controller.notifyWidth(Integer.parseInt(text.getText()));
                }
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if(!text.getText().equals("") && isNumeric(text.getText())) {
                if (text.equals(heightTextField)) {
                    controller.notifyHeight(Integer.parseInt(text.getText()));
                }
                if (text.equals(widthTextField)) {
                    controller.notifyWidth(Integer.parseInt(text.getText()));
                }
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            if(!text.getText().equals("") && isNumeric(text.getText())) {
                if (text.equals(heightTextField)) {
                    controller.notifyHeight(Integer.parseInt(text.getText()));
                }
                if (text.equals(widthTextField)) {
                    controller.notifyWidth(Integer.parseInt(text.getText()));
                }
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        switch (event.getPropertyName()){
            case "addMaze":
            case "removeMaze":
            case "modifyMaze":
                buildFrame((List<String>)event.getNewValue());
                break;
            default:
                break;
        }
    }

    private JComboBox<String> getComboboxFromFile(List<String> list, String selected){
        JComboBox<String> comboBox = new JComboBox<String>();


        int index = list.indexOf(selected);
        for (String s :list){
            comboBox.addItem(s);
        }
        comboBox.setSelectedIndex(index);
        return comboBox;
    }


    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public List<String> getFileNames() {
        List<String> filenames = new ArrayList<>();
        try {
        String ressource = "/mazes";
        InputStream stream = getClass().getResourceAsStream(ressource);

        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        String resource;

            while ((resource = br.readLine()) != null) {

                if(resource.endsWith(".txt"))
                filenames.add(resource);
            }

        br.close();
        stream.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return filenames;
    }

    public List<String> getFileNamesJar() {
        List<String> filenames = new ArrayList<>();
        try {
            CodeSource src = getClass().getProtectionDomain().getCodeSource();
            if (src != null) {
                URL jar = src.getLocation();
                ZipInputStream zip = new ZipInputStream(jar.openStream());
                while (true) {
                    ZipEntry e = zip.getNextEntry();
                    if (e == null)
                        break;
                    String name = e.getName();
                    if(name.endsWith(".txt") && name.startsWith("mazes/")){
                        filenames.add(name.substring(6));
                    }
                }
                zip.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filenames;
    }
}

