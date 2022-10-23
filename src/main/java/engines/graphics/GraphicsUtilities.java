package engines.graphics;

import javax.imageio.ImageIO;
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
     *
     * */
    public static HashMap<String, String> assetsPaths;

    public static final int IMAGE_WIDTH = 60;
    public static final int IMAGE_HEIGHT = 60 ;





    public GraphicsUtilities() {
    }

    public static void setAssetsPaths() {
        assetsPaths = new HashMap<>();
        addToAssetsPath("ubuntu","src/main/resources/sample/image.png",assetsPaths);
        addToAssetsPath("no","src/main/resources/sample/no.png",assetsPaths);


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

        return GraphicsUtilities.scale(image, IMAGE_WIDTH, IMAGE_HEIGHT);
    }
    public static BufferedImage scale(BufferedImage src, int w, int h)
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


    public static void main(String[] args) {
        GraphicsUtilities.setAssetsPaths();
        System.out.println(GraphicsUtilities.assetsPaths);

    }
}
