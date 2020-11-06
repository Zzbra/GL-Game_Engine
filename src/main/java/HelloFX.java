import Systems.GraphicalEngine;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelloFX extends Application {
    private SceneWrapper mainMenuScene;
    private SceneWrapper gameScene;
    private GraphicalEngine graphicEngine;
    @Override
    public void start(Stage stage) {
        graphicEngine = new GraphicalEngine(stage);
        mainMenuScene = SceneMaker.MakeMenuScene();
        gameScene = SceneMaker.MakeGameScene();
        Button startButton = (Button)mainMenuScene.getComponents().get("startGameButton");
        startButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent e) {
                graphicEngine.loadScene(gameScene.getScene());
            }
        });

        graphicEngine.loadScene(mainMenuScene.getScene());



//        String javaVersion = System.getProperty("java.version");
//        String javafxVersion = System.getProperty("javafx.version");
//        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
//        Scene scene = new Scene(new StackPane(l), 640, 480);
//        stage.setScene(scene);
//        stage.show();
       // testMenu(stage);
    }


    public static void main(String[] args) {
        launch();
    }

}