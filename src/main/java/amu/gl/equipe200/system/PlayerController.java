package amu.gl.equipe200.system;

import amu.gl.equipe200.entity.Player;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;

public class PlayerController {
    private Scene scene;

    public PlayerController(Scene scene){
        this.scene = scene;
    }

    public void addAction(EventType<KeyEvent> keyCode, EventHandler<KeyEvent> eventHandler){
        scene.addEventHandler(keyCode, eventHandler);
    }
}
