package gamePlay;

import engines.kernel.Entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Aliens extends Entity {
    public Aliens(int heightEntity, int widthEntity) {
        super(heightEntity, widthEntity, Type.Ai, 1);
        name = "Aliens";
        try {
            BufferedImage image = ImageIO.read(new File("src/main/resources/assets/images/enemigo2.png"));
            graphicalObject.setImage(image);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


