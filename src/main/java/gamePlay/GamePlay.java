package gamePlay;

import engines.graphics.GraphicalEngine;
import engines.kernel.Entity;
import engines.kernel.Kernel;
import engines.kernel.Observer;
import engines.physics.PhysicalEngine;

import javax.sound.midi.Track;
import java.io.IOException;

public class GamePlay {

    public static void main(String[] args) throws IOException {
        Entity  player = new Entity();
        Entity player2 = new Entity();

        Kernel kernel = new Kernel();

        player.register(kernel);
        player2.register(kernel);
        kernel.setSubject(player);
        kernel.setSubject(player2);


        System.out.println(" player 1");
        System.out.println(player);

        kernel.physicalEngine.movable(player,2,2);
        kernel.graphicalEngine.movable(player,4,4);



        System.out.println("player2 ");

        System.out.println(player2);
        kernel.physicalEngine.movable(player2,2,2);
        kernel.graphicalEngine.movable(player2,4,4);


        kernel.updateEntities();


        System.out.println(" ==> player 1 : "+player);
        System.out.println(" ==> player 2 : "+player2);







    }
}
