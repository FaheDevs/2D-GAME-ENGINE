package engines.command;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {


    public boolean UpPressed, downPressed, rightPressed, leftPressed, STyped;
    @Override
    public void keyTyped(KeyEvent keyEvent) {
        char code = keyEvent.getKeyChar();

        if (code == 's'  || code == 'S' ) {
            STyped = true;
        }

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();
        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_UP) {
            UpPressed = true;
        }

        }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();
        int code_c = keyEvent.getKeyChar();

        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_UP) {
            UpPressed = false;
        }
        if (code == 's'  || code == 'S' ) {
            STyped = false;
        }

    }


}

