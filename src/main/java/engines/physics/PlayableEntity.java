package engines.physics;


import static engines.physics.PhysicsUtilities.*;

public class PlayableEntity extends PhysicalEntity {

    public PlayableEntity(int x, int y) {
        super(x, y);
    }


    @Override
    public void move(int x, int y) {
        int newX = x;
        int newY = y;
        if (movement == PhysicsUtilities.UP)
            newY -= SPEED;
        else if (movement == DOWN)
            newY += SPEED;
        moveTo(x, newY);

        if (movement == LEFT)
            newX -= SPEED;
        else if (movement == RIGHT)
            newX += SPEED;
        moveTo(newX, y);


    }
}










