import engines.physics.Direction;
import engines.physics.Entities.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

class EntityTest {

    @Test
    void testMove() {
        Player p = new Player(45, 45);
        Assertions.assertEquals(p.getPosition(), new Point(0,0));
        p.moveTo(45,45);
        Assertions.assertEquals(45, p.getX());
        p.moveTo(90,80);
        Assertions.assertEquals(80, p.getY());
        p.moveTo(10,100);
        Assertions.assertEquals(100, p.getY());
    }

    @Test
    void testMoveTo() {
        Player p = new Player(576, 576);
        p.move(Direction.UP);
        System.out.println(p.getPosition());
    }
}