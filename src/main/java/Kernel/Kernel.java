package Kernel;

import engines.physics.Entities.Player;

import java.awt.*;

public class Kernel extends Thread {

    // the game scene are set depending on the game state
    enum GameState{
        PLAY, PAUSE, STOP, GAMEOVER, VICTORY
    }

    Thread gameThread ;

    public Player player ;




    public Kernel(){

      player= new Player(100,100);

    }

    @Override
    public void run() {
        while (gameThread != null){

        }
    }

    public void update(){


    }
    public void paintComponent(Graphics g){


    }
}
