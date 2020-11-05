import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


import java.awt.*;

public class MenuScene{
    private Scene scene;

    public MenuScene(){
        this.scene = new Scene(new Group());
        Button startGameButton = new Button("Start Game");
        Button controlButton = new Button("Controls");
        Button aboutButton = new Button("About");
        String buttonCss = "-fx-pref-height: 28px; -fx-pref-width: 200px;";
        startGameButton.setStyle(buttonCss);
        controlButton.setStyle(buttonCss);
        aboutButton.setStyle(buttonCss);
        Image img = new Image("PacMan.jpg");
        ImageView imgView = new ImageView(img);
        VBox vBox = new VBox(imgView,startGameButton, controlButton, aboutButton);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(50,10,50,10));
        StackPane panel = new StackPane(vBox);

        panel.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        this.scene = new Scene(panel, 640, 480);
        // Ne marche pas, problème de path probablement, a voir
        //scene.getStylesheets().add("main/java/StyleSheet.css");
    }

    public Scene getScene() {
        return scene;
    }
}
