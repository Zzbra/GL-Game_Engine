package amu.gl.equipe200.graphicengine;

import amu.gl.equipe200.core.Entity;
import amu.gl.equipe200.core.System;

import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;

public class GraphicSystem
    extends System {

    // The graphic engine hold the stage and cached the current dispkayed scene
    private Stage stage;
    private Scene currentScene;

    GraphicSystem (Stage stage) { }

    @Override
    public void onUpdate(long elapsedTime, ArrayList<Entity> affectedEntity) {

    }
}
