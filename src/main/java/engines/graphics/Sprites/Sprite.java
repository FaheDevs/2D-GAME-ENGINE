package engines.graphics.Sprites;

import engines.graphics.GraphicalObject;
import engines.graphics.ObjectsManager;

import java.awt.*;

public class Sprite {


    public int width;
    public int  height ;
    public int spriteX;
    public int spriteY;

    GraphicalObject graphicalObject;

    public Sprite(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean collidesWith(Sprite sp){

        Boolean collision = false ;
        Rectangle rect1 = new Rectangle(spriteX, spriteY, width/2, height/2);

        Rectangle rect2 = new Rectangle(sp.spriteX, sp.spriteY, sp.width, sp.height);

        if(rect1.intersects(rect2)) {
            collision= true ;
        }
        return  collision;
    }


}
