package gamePlay;

import engines.kernel.Entity;
import engines.kernel.Kernel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Bullet extends Entity {

    public Bullet() {
        name = "Bullet";
        try {
            BufferedImage image = ImageIO.read(new File("src/main/resources/assets/images/shot.png"));
            graphicalObject.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void tick() {
        y = y  - 1;
    }
}
