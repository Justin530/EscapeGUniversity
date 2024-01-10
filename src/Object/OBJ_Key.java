package Object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Key extends SuperObject{
    public OBJ_Key(GamePanel gp) {
        super(gp);
        name = "Key";

        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/object/key.png")));
            img = uTool.scaleImage(img, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        collisionOn = true;
    }

    public void interact() {
        gp.player.isHoldKey=true;
        gp.ui.addMessage("Key Found,Escape Now !");
    }
}
