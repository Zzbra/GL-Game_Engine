package amu.gl.equipe200.core;

public class GameWorldMaker {

    public static GameWorld MakeMenuScene(){
        return new MainMenuScene();
    }

    public static GameWorld MakeGameScene(){
        return new GameScene();
    }

}
