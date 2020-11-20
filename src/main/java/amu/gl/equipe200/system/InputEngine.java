package amu.gl.equipe200.system;

import amu.gl.equipe200.core.Component.Component;
import amu.gl.equipe200.core.Component.InputComponent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class InputEngine {
      HashSet<String> pressedKey;
      private Scene scene;

      public InputEngine(Scene scene){
          this.pressedKey = new HashSet<>();
          this.scene = scene;
          init();
      }

      private void init(){
          this.scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
              pressedKey.add(key.getCode().toString());
          });
      }

      public void update(List<Component> componentList){
          for(Component component : componentList){
              component.updateBy(this);
          }
          pressedKey.clear();
      }

      public void update(InputComponent inputComponent){
          for(String key : pressedKey){
              inputComponent.reactToInput(key);
          }
      }

}
