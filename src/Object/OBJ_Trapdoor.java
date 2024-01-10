package Object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Trapdoor extends SuperObject{
    public OBJ_Trapdoor(GamePanel gp) {
        super(gp);
        worldLoc.setXPosition(47*gp.tileSize);
        worldLoc.setYPosition(46*gp.tileSize);
        name = "Escape";

        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/object/locked_door.png")));
            img = uTool.scaleImage(img, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        collisionOn = true;
    }

    public void interact() {
        if(gp.player.isHoldKey) {
            gp.ui.addMessage( "Finally Rush Out of G University !");
            gp.objects[0][4] = null ;
            gp.gameState = gp.endingState;
            gp.stopMusic();
            gp.playMusic(1);
        }else{
            gp.ui.addMessage("You Need to Find the Key to Open it !");
        }
    }
}
