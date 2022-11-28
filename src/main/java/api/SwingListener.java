package api;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public class SwingListener {
    private static SwingListener instance;
    private SwingListener() {}
    protected static SwingListener getInstance() {
        if (instance == null) instance = new SwingListener();
        return instance;
    }

    public void addKeyListener(KeyListener listener) {
        SwingWindow.getInstance().getWindow().addKeyListener(listener);
    }
    public void removeKeyListener(KeyListener listener) {
        SwingWindow.getInstance().getWindow().removeKeyListener(listener);
    }
    public void addMouseListener(MouseListener listener) {
        SwingWindow.getInstance().getWindow().addMouseListener(listener);
    }
    public void removeMouseListener(MouseListener listener) {
        SwingWindow.getInstance().getWindow().removeMouseListener(listener);
    }

}
