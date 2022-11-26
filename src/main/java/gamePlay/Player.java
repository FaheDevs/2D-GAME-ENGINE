package gamePlay;


import engines.graphics.GraphicalObject;
import engines.kernel.Entity;
import engines.kernel.Kernel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {

    public Player() throws IOException {
        name = "Player";
        BufferedImage image = ImageIO.read(new File("src/main/resources/assets/images/Spacecraft/00.png"));
        graphicalObject.setImage(image);
    }


    public void move(Entity entity, GamePlay.MoveDirection direction) {
        int newX;
        if (direction == GamePlay.MoveDirection.LEFT) {
            newX = entity.x - 2;
            entity.move(newX, entity.y);
        }
        if (direction == GamePlay.MoveDirection.RIGHT) {
            newX = entity.x + 2;
            entity.move(newX, entity.y);
        }

    }


}
