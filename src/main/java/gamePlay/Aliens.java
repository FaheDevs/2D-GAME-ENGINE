package gamePlay;

import engines.kernel.Entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

public class Aliens extends Entity {
    boolean killed;
    public Aliens(int heightEntity, int widthEntity) {
        super(heightEntity, widthEntity, Type.Ai, 1);
        name = "Aliens";
        value = 10;
        killed = false;
        try {
            BufferedImage image = ImageIO.read(new File("src/main/resources/assets/images/Aliens/alienHaut1.png"));
            graphicalObject.setImage(image);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


