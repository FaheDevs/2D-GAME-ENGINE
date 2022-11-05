package Kernel;

import java.awt.*;

public class Kernel extends Thread {
    Thread gameThread ;

    public Kernel(){
        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {
        while (gameThread != null){
            // UPDATE INFORMATION SUCH AS CHARACTER POSITION
            // DRAW ON THE SCREEN
        }
    }

    public void update(){


    }
    public void paintComponent(Graphics g){

    }
}
