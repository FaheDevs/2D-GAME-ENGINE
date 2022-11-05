package Kernel;

import java.awt.*;

public class Kernel extends Thread {


    enum GameStates{
        InGAME,
        PAUSE,
    }

    Thread gameThread ;

    public Kernel(){
        gameThread = new Thread(this);
        gameThread.start();

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
