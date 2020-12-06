package amu.gl.equipe200.graphicsengine;

import javafx.scene.image.Image;

import java.net.URL;
import java.util.HashMap;

public class ImagesManager {

    static final String PATH = "/ressources/images/";

    // cache for the images
    HashMap<String, Image> cachedImage = new HashMap<>();

    public Image getImage(String name) {
        //System.out.println(name);
        if (this.cachedImage.containsKey(name)) return cachedImage.get(name);
        Image image = new Image(name);
        this.cachedImage.put(name, image);
        return image;
    }

    public void clearCache() {
        cachedImage.clear();
    }
}
