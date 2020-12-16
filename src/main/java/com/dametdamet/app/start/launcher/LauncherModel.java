package com.dametdamet.app.start.launcher;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LauncherModel {

    private List<String> mazes;

    private int height = 800;

    private int width = 800;

    private boolean exit = false;
    private boolean launch = false;

    private PropertyChangeSupport support;

    public LauncherModel(String[] initialMazes){
        mazes = new ArrayList<>();
        Collections.addAll(mazes, initialMazes);
        support = new PropertyChangeSupport(this);
    }

    public LauncherModel(){
        mazes = new ArrayList<>();
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public int getWidth() {
        return width;
    }

    public synchronized boolean isExit(){
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public boolean isLaunch() {
        return launch;
    }

    public void setLaunch(Boolean launch){
        this.launch = launch;
    }

    public int getHeight() {
        return height;
    }

    public String getMazes(int index) {
        return mazes.get(index);
    }

    public void addMaze(int index,String maze){
        if(!(index < 0 || index > mazes.size())) {
            List<String> list = new ArrayList<>();
            mazes.add(index, maze);
            support.firePropertyChange("addMaze", list, mazes);
        }
    }

    public void removeMaze(int index){
        if(!(index < 0 || index > mazes.size())) {
            List<String> list = new ArrayList<>();
            mazes.remove(index);
            support.firePropertyChange("removeMaze", list, mazes);
        }
    }

    public int getSizeMazes(){
        return mazes.size();
    }



    public void modifyMaze(int index,String newMaze){
        if(!(index < 0 || index > mazes.size())){
            List<String> list = new ArrayList<>();
            mazes.set(index,newMaze);
            support.firePropertyChange("modifyMaze",list,mazes);
        }

    }

    public void setHeight(int height) {
        if(width > 0) {
            this.height = height;
            support.firePropertyChange("setHeight", -height, height);
        }
    }

    public void setWidth(int width) {
        if(width > 0) {
            this.width = width;
            support.firePropertyChange("setWidth", -width, width);
        }
    }

    public List<String> getMazes(){
        return mazes;
    }

    public String[] getFileMazes(){
        String[] files = new String[mazes.size()];
        for(int i =0 ; i < mazes.size();  i++){
            files[i] = "mazes/"+mazes.get(i);
        }
        return files;
    }
}
