package Systems;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class GraphicalEngine {
    private Stage stage;
    private Scene currentScene;
    public GraphicalEngine(Stage stage){
        this.stage = stage;
    }

    public void loadScene(Scene scene){
        this.currentScene = scene;
        this.stage.setScene(scene);
        this.stage.show();
    }
}
