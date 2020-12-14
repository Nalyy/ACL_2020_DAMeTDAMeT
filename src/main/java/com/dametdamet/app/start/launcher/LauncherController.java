package com.dametdamet.app.start.launcher;

public class LauncherController {

        private LauncherModel launcherModel;
        private LauncherView view;

        public LauncherController(LauncherModel launcherModel){
            this.launcherModel = launcherModel;
        }

        public void setView(LauncherView view){
            this.view = view;
        }

        public void notifyWidth(int width){
            launcherModel.setWidth(width);
        }

        public void notifyHeight(int height){
            launcherModel.setHeight(height);
        }

        public void notifyAddMaze(int index){
            launcherModel.addMaze(index, "maze_1.txt");
        }

        public void notifyRemoveMaze(int index){
            launcherModel.removeMaze(index);
        }

        public void notifyModifyMaze(int index,String newMaze){
            launcherModel.modifyMaze(index,newMaze);
        }

        public void notifyLaunch(){
            launcherModel.setLaunch(true);
        }

        public void notifyExit(){
            launcherModel.setExit(true);
        }
}
