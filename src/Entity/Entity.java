package Entity;

import java.awt.image.BufferedImage;
import java.awt.*;

public class Entity {
    public Location loc;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public Direction direction;

    public int spriteCount = 0;
    public int spriteNum = 1;
    public Rectangle hitBox;
    public boolean collisionOn = false;
}
