import engines.physics.Direction;
import engines.physics.entities.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

class EntityTest {

    @Test
    void testMove() {
        Player p = new Player(45, 45);
        Assertions.assertEquals(p.getPosition(), new Point(45,45));
        p.moveTo(90,80);
        Assertions.assertEquals(p.getPosition(), new Point(90,80));
        p.moveTo(10,100);
        Assertions.assertEquals(p.getPosition(), new Point(10,100));
    }

    @Test
    void testMoveTo() {
        Player p = new Player(576, 576);
        p.move(Direction.UP);
        System.out.println(p.getPosition());
    }
}