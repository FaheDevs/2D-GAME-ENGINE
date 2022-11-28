package api;

import javax.swing.*;
import java.awt.*;

public class SwingWindow {
    private static SwingWindow instance;
    private SwingWindow() {}

    public static SwingWindow getInstance() {
        if (instance == null) instance = new SwingWindow();
        return instance;
    }

    private final JFrame window = new JFrame();

    public void initWindow(String title) {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle(title);
        window.setVisible(true);
    }

    public void stopWindow() {
        window.dispose();
    }
    public void refreshWindow() {
        SwingScene currentScene = getCurrentScene();
        if (currentScene != null)
            currentScene.repaint();
    }

    public void showScene(SwingScene scene) {
        window.getContentPane().removeAll();
        window.getContentPane().add(scene, 0);
        window.pack();
        window.setLocationRelativeTo(null);
        setBackgroundColor(scene.backgroundColor);

    }

    public void setBackgroundColor(Color color) {
        window.getContentPane().setBackground(color);
    }

    public SwingScene getCurrentScene() {
        return (SwingScene) window.getContentPane().getComponent(0);
    }
    public JFrame getWindow() { return window; }


}
