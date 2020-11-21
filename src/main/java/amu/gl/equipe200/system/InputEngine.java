package amu.gl.equipe200.system;

import amu.gl.equipe200.core.Component.Component;
import amu.gl.equipe200.core.Component.InputComponent;
import amu.gl.equipe200.core.Component.Interfaces.IOInterface;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class InputEngine {
      HashSet<String> pressedKeys;
      private Scene scene;
      private HashSet<IOInterface> ioEntities;

      public InputEngine(Scene scene){
          this.pressedKeys = new HashSet<>();
          this.scene = scene;
          this.ioEntities = new HashSet<>();
          init();
      }

      public void addIOEntity(IOInterface ioEntity){
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

      private void init(){
          this.scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
              pressedKeys.add(key.getCode().toString().toUpperCase());
          });
      }

      public void update(List<Component> componentList){
          for(Component component : componentList){
              component.updateBy(this);
          }
          pressedKeys.clear();
      }

      public void update(InputComponent inputComponent){
          for(String key : pressedKeys){
              inputComponent.reactToInput(key);
          }
      }

}
