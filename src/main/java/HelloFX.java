import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloFX extends Application {
    private Scene mainMenuScene;
    @Override
    public void start(Stage stage) {
        MenuScene MS = new MenuScene();
        mainMenuScene = MS.getScene();
        stage.setScene(mainMenuScene);
        stage.show();
//        String javaVersion = System.getProperty("java.version");
//        String javafxVersion = System.getProperty("javafx.version");
//        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
//        Scene scene = new Scene(new StackPane(l), 640, 480);
//        stage.setScene(scene);
//        stage.show();
       // testMenu(stage);
    }

    public void testMenu(Stage stage){
        MenuScene MS = new MenuScene();
        stage.setScene(MS.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}