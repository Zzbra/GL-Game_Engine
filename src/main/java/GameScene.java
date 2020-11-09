import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class GameScene extends SceneWrapper {
    public GameScene(){
        super();
        Scene gameScene;
        Rectangle rectangle = new Rectangle();
        Image img = new Image("Pac.png");
        rectangle.setFill(new ImagePattern(img));
        rectangle.setX(75.0f);
        rectangle.setY(75.0f);
        rectangle.setWidth(150.0f);
        rectangle.setHeight(150.0f);
        getComponents().put("Shape", rectangle);
        // ImageView imgView = new ImageView(img);
        //VBox vBox = new VBox(imgView);
        //vBox.setAlignment(Pos.CENTER);
        Group group = new Group(rectangle);
        //group.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        gameScene = new Scene(group, 640, 480);
        super.setScene(gameScene);
    }
}
