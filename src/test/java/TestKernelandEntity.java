import engines.kernel.Entity;
import engines.kernel.Kernel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestKernelandEntity {



    @Test
    public void testKernelEntity() throws IOException {
        Entity player = new Entity();
        Entity player2 = new Entity();

        Kernel kernel = new Kernel();

        player.register(kernel);
        player2.register(kernel);
        kernel.setSubject(player);
        kernel.setSubject(player2);

        kernel.physicalEngine.movable(player,2,2);
        kernel.graphicalEngine.movable(player,4,4);
        kernel.physicalEngine.movable(player2,2,2);
        kernel.graphicalEngine.movable(player2,4,4);

        kernel.updateEntities();

        Assertions.assertEquals(player.physicalObject.x,2);
        Assertions.assertEquals(player.physicalObject.y,2);
        Assertions.assertEquals(player2.physicalObject.x,2);
        Assertions.assertEquals(player2.physicalObject.x,2);
        Assertions.assertEquals(player.graphicalObject.position.x,4);
        Assertions.assertEquals(player.graphicalObject.position.x,4);



    }
}
