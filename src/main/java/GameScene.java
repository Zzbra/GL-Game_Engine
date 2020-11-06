import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GameScene extends SceneWrapper {
    public GameScene(){
        super();
        Scene gameScene;
        Image img = new Image("PacMan.jpg");
        ImageView imgView = new ImageView(img);
        VBox vBox = new VBox(imgView);
        vBox.setAlignment(Pos.CENTER);
        StackPane panel = new StackPane(vBox);
        panel.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        gameScene = new Scene(panel, 640, 480);
        super.setScene(gameScene);
    }
}
