package engines.physics;

import java.awt.*;

public class Collision {
    public static boolean checkCollisionWorld(int newX, int newY){
        return  !((newY >= 500 -32 || newY < 0) || (newX > 528 -64  || newX < 0));
    }
    public static boolean checkCollisionObject(Point pE1, Point pE2){
        return  !((pE1.y >= pE2.y + 32 || pE1.y <= pE2.y - 32) || (pE1.x + 32 <= pE2.x || pE1.x >= pE2.x + 32));
    }

}
