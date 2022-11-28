package gamePlay;

import engines.kernel.Entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Bullet extends Entity {

    boolean isPressed;
    public Bullet(int heightEntity, int widthEntity) {
        super(heightEntity, widthEntity, Type.Physical, 3);
        isPressed = false;
        name = "Bullet";
        try {
            BufferedImage image = ImageIO.read(new File("src/main/resources/assets/images/shot.png"));
            graphicalObject.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void tick() {
        if(isPressed){
            int newY = y - this.physicalObject.speed;
            setPositions(x,newY);

        } else{
            int newY = y + this.physicalObject.speed;
            setPositions(x,newY);
        }
    }
}
