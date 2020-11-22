package amu.gl.equipe200.graphicsengine;

import javafx.scene.image.Image;

import java.util.HashMap;

public class ImagesManager {

    // cache for the images
    HashMap<String, Image> cachedImage;

    public Image getImage(String name) {
        if (this.cachedImage.containsKey(name)) return cachedImage.get(name);
        Image image = new Image(name);
        this.cachedImage.put(name, image);
        return image;
    }

    public void clearCache() {
        cachedImage.clear();
    }
}
