package amu.gl.equipe200.inputengine;


import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;

public class InputEngine {
    HashSet<String> pressedKeys;
    private Scene linkedScene;
    private HashSet<IOInterface> ioEntities;

    public InputEngine(){
        this.pressedKeys = new HashSet<>();
        this.ioEntities = new HashSet<>();
        this.linkedScene = null;
    }

    public void attachToScene(Scene scene){
        this.linkedScene = scene;
        this.linkedScene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            pressedKeys.add(key.getCode().toString().toUpperCase());
        });
    }

    public void loadGameWorld(HashSet<IOInterface> ioEntities) {
        this.ioEntities.clear();
        for (IOInterface io : ioEntities) {
            this.ioEntities.add(io);
        }
    }

    public void registerEntity(IOInterface ioEntity){
          ioEntities.add(ioEntity);
      }

    public void update(){
        for(IOInterface ioEntity : ioEntities){
            for(String string : pressedKeys) {
                ioEntity.reactToInput(string);
            }
        }
        pressedKeys.clear();
    }
}
