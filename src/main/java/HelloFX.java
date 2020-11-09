import Systems.GraphicalEngine;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.*;

public class HelloFX extends Application {
    private SceneWrapper mainMenuScene;
    private SceneWrapper gameScene;
    private GraphicalEngine graphicEngine;
    private Timeline gameLoop;
    @Override
    public void start(Stage stage) {
        final Duration fps = Duration.millis(1000/60);
        final KeyFrame oneFrame = new KeyFrame(fps, evt -> update());

        graphicEngine = new GraphicalEngine(stage);
        mainMenuScene = SceneMaker.MakeMenuScene();
        gameScene = SceneMaker.MakeGameScene();
        Button startButton = (Button)mainMenuScene.getComponents().get("startGameButton");
        startButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent e) {
                graphicEngine.loadScene(gameScene.getScene());
                gameLoop = new Timeline();
                gameLoop.getKeyFrames().add(oneFrame);
                gameLoop.setCycleCount(Animation.INDEFINITE);
                gameLoop.play();
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

    private void update(){
        System.out.println("frame");
        Rectangle rect = (Rectangle) gameScene.getComponents().get("Shape");
        rect.setX(rect.getX()+1);
    }


    public static void main(String[] args) {
        launch();
    }

}