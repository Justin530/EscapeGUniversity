package Object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Potion extends SuperObject{
    public OBJ_Potion(GamePanel gp) {
        super(gp);
        name = "Potion";

        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/object/potion.png")));
            img = uTool.scaleImage(img, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        collisionOn = true;
    }

    public void interact() {
        gp.player.HP = gp.player.maxHP;
    }
}
