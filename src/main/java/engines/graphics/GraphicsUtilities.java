package engines.graphics;

import javax.imageio.ImageIO;
import javax.swing.text.html.parser.Entity;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public  class GraphicsUtilities {


    /**
     * utility class for the graphics engine
     * initialize the paths to the pictures into a hashMap
     * helps access to path of image based on image name .
     *
     * helps with creation of a graphical object
     *
     * helps with uploading and scaling the image
     * */
    public static HashMap<String, String> assetsPaths = new HashMap<>();

    public static HashMap<Integer , BufferedImage[]> assetsImages = new HashMap<>();

    // 0 -> PLAYER
    // 1 -> MONSTER 1
    // 2 -> MONSTER 2
    // 3 -> MONSTER 3


    public static final int IMAGE_WIDTH = 64;
    public static final int IMAGE_HEIGHT = 64 ;


    public static void setAssetsPaths() {
        addToAssetsPath("pacman","src/main/resources/pactiles/ghost2.png",assetsPaths);

    }

    public static void addToAssetsPath(String image,String path,HashMap<String,String> assets){
        assets.put(image,path);
    }

    public static GraphicalObject createGraphicalObject(String name , String path) throws IOException {
        return  new GraphicalObject(GraphicsUtilities.upload(path),name);
    }

    public static BufferedImage upload(String path ) throws IOException {
        BufferedImage image = null;
        image = ImageIO.read(new File(path));
        return image;
    }
    /*public static BufferedImage scale(BufferedImage src, int w, int h)
    {
        BufferedImage img =
                new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int x, y;
        int ww = src.getWidth();
        int hh = src.getHeight();
        int[] ys = new int[h];
        for (y = 0; y < h; y++)
            ys[y] = y * hh / h;
        for (x = 0; x < w; x++) {
            int newX = x * ww / w;
            for (y = 0; y < h; y++) {
                int col = src.getRGB(newX, ys[y]);
                img.setRGB(x, y, col);
            }
        }
        return img;
    }

     */



}
