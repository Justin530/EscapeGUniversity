package Entity;

import Main.GamePanel;

import java.awt.image.BufferedImage;
import java.awt.*;

public class Entity {
    public Location loc;
    public int speed;
    public GamePanel gamePanel;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public Direction direction;

    public int spriteCount = 0;//count for sprite animation
    public int spriteNum = 1;//sprite number, decide which sprite to draw(1 or 2)
    public Rectangle hitBox;
    public boolean collisionOn = false;
}
