package Object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends SuperObject{

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        name = "Heart";

        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/object/heart_full.png")));
            img2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/object/heart_half.png")));
            img3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/object/heart_blank.png")));
            img = uTool.scaleImage(img, gp.tileSize, gp.tileSize);
            img2 = uTool.scaleImage(img2, gp.tileSize, gp.tileSize);
            img3 = uTool.scaleImage(img3, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
