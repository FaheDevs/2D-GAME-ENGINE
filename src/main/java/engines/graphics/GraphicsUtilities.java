package engines.graphics;

import javax.imageio.ImageIO;
import javax.swing.text.html.parser.Entity;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class GraphicsUtilities {




    public static final int ORIGINAL_TILE_SIZE = 64; // 64 x 64 TILES
    public static final int SCALE = 1;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public static final int MAX_SCREEN_COL = 10;
    public static final int MAX_SCREEN_ROW = 10;
    public static final int SCENE_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public static final int SCENE_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    public static int FPS = 60;
    /**
     * utility class for the graphics engine
     * initialize the paths to the pictures into a hashMap
     * helps access to path of image based on image name .
     * helps with creation of a graphical object
     * helps with uploading and scaling the image
     */
    public static HashMap<String, String> assetsPaths = new HashMap<>();

    public static HashMap<Integer, BufferedImage[]> assetsImages = new HashMap<>();

    public static void setAssetsPaths() {
        addToAssetsPath("pacman", "src/main/resources/pactiles/ghost2.png", assetsPaths);
    }

    public static void addToAssetsPath(String image, String path, HashMap<String, String> assets) {
        assets.put(image, path);
    }

    public static GraphicalObject createGraphicalObject(String name, String path) throws IOException {
        return new GraphicalObject(GraphicsUtilities.upload(path), name);
    }

    public static BufferedImage upload(String path) throws IOException {
        BufferedImage image = null;
        image = ImageIO.read(new File(path));
        return image;
    }


}
