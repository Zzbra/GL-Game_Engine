package amu.gl.equipe200.system;

import amu.gl.equipe200.entity.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;

import java.util.HashMap;

public class PlayerController {
    private HashMap<KeyCode, EventHandler<ActionEvent>> eventMap;
    private Player player;


    public PlayerController(Player player){
        this.player = player;
        this.eventMap = new HashMap<>();
    }

    public void addAction(KeyCode keyCode, EventHandler<ActionEvent> eventHandler){
        eventMap.put(keyCode, eventHandler);
    }
}
