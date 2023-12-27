package Main;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * a class that contains some useful methods
 */
public class UtilityTool {

    public BufferedImage scaleImage(BufferedImage image, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();

        return scaledImage;
    }
}
