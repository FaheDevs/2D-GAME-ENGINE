package gamePlay;


import engines.graphics.GraphicalObject;
import engines.kernel.Entity;
import engines.kernel.Kernel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {

    public Player(int heightEntity, int widthEntity) throws IOException {
        super(heightEntity, widthEntity, Type.Physical, 2);
        name = "Player";
        BufferedImage image = ImageIO.read(new File("src/main/resources/assets/images/Spacecraft/00.png"));
        graphicalObject.setImage(image);
    }



}
