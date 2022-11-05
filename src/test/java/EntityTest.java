import engines.physics.PlayableEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

class EntityTest {

    @Test
    void testMove() {
        PlayableEntity e = new PlayableEntity(45, 45);

        e.moveTo(0,0);
        Assertions.assertEquals(e.getPosition(), new Point(0,0));
        e.moveTo(45,45);
        Assertions.assertEquals(45, e.getX());
        e.moveTo(90,80);
        Assertions.assertEquals(80, e.getY());
        e.moveTo(10,100);
        Assertions.assertEquals(100, e.getY());
    }

    @Test
    void testMoveTo() {
        PlayableEntity e = new PlayableEntity(45, 45);
        e.moveTo(50, 50);
        Assertions.assertEquals(50, e.getX());
        Assertions.assertEquals(50, e.getY());
    }
}