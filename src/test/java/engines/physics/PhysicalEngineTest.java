package engines.physics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PhysicalEngineTest {

     PhysicalEngine physicalEngine = new PhysicalEngine();
     PhysicalObject physicalObject ;


    @Test
    void movable() {
         physicalObject = new PhysicalObject(10,10);

        physicalEngine.movable(physicalObject,100,100);

        Assertions.assertEquals(100,physicalObject.x);
        Assertions.assertEquals(100,physicalObject.y);

        Assertions.assertEquals(new Point(100,100),physicalObject.position);



    }

    @Test
    void isCollide() {
        physicalObject = new PhysicalObject(10,10);

        ArrayList<PhysicalObject> physicalObjects = new ArrayList<>();


        physicalObjects.add(new PhysicalObject(200,150));

        physicalObjects.add(new PhysicalObject(100,100));
        physicalObjects.add(new PhysicalObject(90,90));


        Assertions.assertFalse(physicalEngine.isCollide(physicalObject,10,10,physicalObjects));



        Assertions.assertFalse(physicalEngine.isCollide(physicalObject,40,40,physicalObjects));
        Assertions.assertFalse(physicalEngine.isCollide(physicalObject,50,50,physicalObjects));
        Assertions.assertFalse(physicalEngine.isCollide(physicalObject,60,60,physicalObjects));
        Assertions.assertFalse(physicalEngine.isCollide(physicalObject,70,70,physicalObjects));
        Assertions.assertFalse(physicalEngine.isCollide(physicalObject,80,80,physicalObjects));


        Assertions.assertTrue(physicalEngine.isCollide(physicalObject,90,90,physicalObjects));
        Assertions.assertTrue(physicalEngine.isCollide(physicalObject,100,100,physicalObjects));
        Assertions.assertTrue(physicalEngine.isCollide(physicalObject,200,150,physicalObjects));


    }


    @Test
    void isCollideRight() {
        physicalObject = new PhysicalObject(10,10);

    }

    @Test
    void isCollideLeft() {
        physicalObject = new PhysicalObject(10,10);

    }

    @Test
    void collideObjectToObject() {
        physicalObject = new PhysicalObject(10,10);

    }

    @Test
    void move() {
        physicalObject = new PhysicalObject(10,10);
        physicalEngine.movable(physicalObject,100,100);

        assertEquals(new Point(100,100),physicalObject.position);
        assertEquals(100,physicalObject.x);
        assertEquals(100,physicalObject.y);
    }
}