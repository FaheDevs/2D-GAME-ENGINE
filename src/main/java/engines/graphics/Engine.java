package engines.graphics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serial;
import java.text.MessageFormat;
import java.util.Objects;

public class Engine extends JPanel {

    @Serial
    private static final long serialVersionUID = 4242L;
    private transient BufferedImage image;

    public Engine() throws IOException {
        Logger logger = LogManager.getLogger(this.getClass());
        logger.debug("Construct a MyJavaPanel");
        String path = "src/main/resources/sample/image.png";
        if (logger.isDebugEnabled()) {
            String message = MessageFormat.format("Loading image at path {0}", path);
            logger.debug(message);

        } try {
            setImage(0,path);
        } catch (Exception ex) {
            String message = MessageFormat.format("Error: Cannot load image at path: {0}", path);
            logger.error(message, ex);
        }
    }

    @Override
    protected void printComponent(Graphics g) {
        super.printComponent(g);
        g.drawImage(image, 50, 50, null);

    }


    public void setImage(int i , String path ) throws IOException {
        ObjectsManager objectsManager = new ObjectsManager();
        objectsManager.setObjectEImage(i,path);
        image = objectsManager.getGraphicalObjects(i);

    }
}
