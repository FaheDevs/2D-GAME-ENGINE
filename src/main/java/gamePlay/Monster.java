package gamePlay;

import engines.kernel.Entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Monster extends Entity {
    public Monster() {
        name = "Monster";
        try {
            BufferedImage image = ImageIO.read(new File("src/main/resources/assets/images/enemigo2.png"));
            graphicalObject.setImage(image);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


