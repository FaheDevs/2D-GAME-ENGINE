package gamePlay;

import engines.graphics.GraphicalEngine;
import engines.kernel.Entity;
import engines.kernel.Kernel;
import engines.kernel.Observer;
import engines.physics.PhysicalEngine;

import java.io.IOException;

public class GamePlay {

    public static void main(String[] args) throws IOException {
        Entity  entity = new Entity();


        Observer kernel = new Kernel();

        PhysicalEngine physicalEngine = new PhysicalEngine() ;
        GraphicalEngine graphicalEngine = new GraphicalEngine();

        entity.register(kernel);
        kernel.setSubject(entity);

        System.out.println(physicalEngine.hashCode());

        physicalEngine.movable(entity,2,2);
        System.out.println(entity.physicalObject.hashCode());
        graphicalEngine.movable(entity,4,4);
        kernel.updateEntities();






    }
}
