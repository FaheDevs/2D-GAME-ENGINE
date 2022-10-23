import engines.graphics.GraphicsUtilities;
import engines.physics.Entity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sample.MyJavaFrame;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    void testMove() {
        Entity e = new Entity(45, 45);
        e.move(0);
        Assertions.assertEquals(05, e.getX());
        e.move(1);
        Assertions.assertEquals(45, e.getX());
        e.move(2);
        Assertions.assertEquals(05, e.getY());
        e.move(3);
        Assertions.assertEquals(45, e.getY());
    }

    @Test
    void testMoveTo() {
        Entity e = new Entity(45, 45);
        e.moveTo(50, 50);
        Assertions.assertEquals(50, e.getX());
        Assertions.assertEquals(50, e.getY());
    }
}