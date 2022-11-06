package engines.graphics.GameScenes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WelcomeScene implements KeyListener {


    Graphics gc;

    public WelcomeScene(Graphics gc) {
        this.gc = gc;
        showWelcomeMessage();
    }

    private void showWelcomeMessage() {

        gc.drawString("press enter to play ", 325, 275);

    }

    public JPanel jPanel = new JPanel(true) {
        @Override
        public void paintComponent(Graphics g) {
//            System.out.println("player Position  = " + player.getPosition());
//            g.drawImage(objectsManager.getGraphicalObjects("pacman"), player.getX(), player.getY(), null);

        }

        @Override
        public void setFocusable(boolean b) {
            super.setFocusable(b);
        }

    };

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
