package sample;

import engines.graphics.Engine;

import java.io.IOException;
import java.io.Serial;
import javax.swing.JFrame;


/**
 * An extended version of javax.swing.JFrame containing a panel to draw images.
 */
public class MyJavaFrame extends JFrame {

  @Serial
  private static final long serialVersionUID = 42L;
  /**
   * Constructs a new visible frame.
   */

  public MyJavaFrame() throws IOException {
    setTitle("Main window");
    setSize(400, 400);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(new Engine());
    setVisible(true);
  }

  public static void main(String[] args) throws IOException {
    new MyJavaFrame();
  }
}
