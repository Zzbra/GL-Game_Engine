package Systems;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class GraphicalEngine {
    private Stage stage;
    public GraphicalEngine(Stage stage){
        this.stage = stage;
    }

    public void loadScene(Scene scene){
        this.stage.setScene(scene);
        this.stage.show();
    }
}
