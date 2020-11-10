import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Enemy extends BaseEntity {

    public Enemy(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage) {
        super(layer, image, x, y, r, dx, dy, dr, health, damage);
    }

    @Override
    public void checkRemovability() {
        if( Double.compare( getY(), Settings.SCENE_HEIGHT) > 0) {
            setRemovable(true);
        }
    }

    @Override
    public Game.Tag getTag() {
        return Game.Tag.ENEMY;
    }

    @Override
    public ArrayList<Game.Tag> collideWithTags() {
        return new ArrayList<Game.Tag>();
    }

    @Override
    public void onCollide(BaseEntity entity) {

    }

    @Override
    public void onCollisionStay(BaseEntity entity2) {}
}