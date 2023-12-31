package Object;

import Entity.*;
import Main.GamePanel;
import Main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    GamePanel gp;
    UtilityTool uTool = new UtilityTool();
    public BufferedImage img, img2, img3;
    public String name;
    public boolean collisionOn = false;
    public Location worldLoc = new Location(0, 0);
    public Rectangle hitBox = new Rectangle(0, 0, 48, 48);
    public int hitBoxDefaultX, hitBoxDefaultY;

    public SuperObject(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2d, GamePanel gp) {
        int worldX = worldLoc.getXPosition();
        int worldY = worldLoc.getYPosition();
        int screenX = worldLoc.getXPosition() - gp.player.worldLoc.getXPosition() + gp.player.screenLoc.getXPosition();
        int screenY = worldLoc.getYPosition() - gp.player.worldLoc.getYPosition() + gp.player.screenLoc.getYPosition();

        if (worldX + gp.tileSize > gp.player.worldLoc.getXPosition() - gp.player.screenLoc.getXPosition() &&
            worldX - gp.tileSize < gp.player.worldLoc.getXPosition() + gp.player.screenLoc.getXPosition() &&
            worldY + gp.tileSize > gp.player.worldLoc.getYPosition() - gp.player.screenLoc.getYPosition() &&
            worldY - gp.tileSize < gp.player.worldLoc.getYPosition() + gp.player.screenLoc.getYPosition()) {
                g2d.drawImage(img, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
