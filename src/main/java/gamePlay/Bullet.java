package gamePlay;

import engines.kernel.Entity;
import engines.kernel.Kernel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Bullet extends Entity {

    public Bullet(Kernel kernel) {
        register(kernel);
        name = "Bullet";

    }


    public void tick() {
        y = -10;
    }
}
