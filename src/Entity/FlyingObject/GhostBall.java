package Entity.FlyingObject;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GhostBall extends FlyingObject {
    public BufferedImage die_image;
    public GhostBall(GamePanel gp) {
        super(gp);

        name = "Rock";
        speed = 8;
        maxHP = 80;
        HP = maxHP;
        attack = 1;

        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/res/FlyingObject/ghostball_up_1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/res/FlyingObject/ghostball_up_2.png", gp.tileSize, gp.tileSize);
        down1 = setup("/res/FlyingObject/ghostball_down_1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/res/FlyingObject/ghostball_down_2.png", gp.tileSize, gp.tileSize);
        right1 = setup("/res/FlyingObject/ghostball_right_1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/res/FlyingObject/ghostball_right_2.png", gp.tileSize, gp.tileSize);
        left1 = setup("/res/FlyingObject/ghostball_left_1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/res/FlyingObject/ghostball_left_2.png", gp.tileSize, gp.tileSize);
        die_image = setup("/res/FlyingObject/ghostball_die.png", gp.tileSize, gp.tileSize);
    }

    public void update() {
        boolean contactPlayer = gp.collisionDetector.checkPlayer(this);
        if (contactPlayer && !gp.player.invincible) {
            damagePlayer(attack);
            dying = true;
        }

        gp.collisionDetector.checkTile(this);
        if (collisionOn) {
            dying = true;
            collisionOn = false;
        }
        //移动时，根据direction变化x，y坐标
        move(this);

        HP --;
        if (HP <= 0) {
            dying = true;
        }

        spriteCount ++;
        if (spriteCount == 10) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCount = 0;
        }
    }

    public void draw(Graphics2D g2d) {
        BufferedImage img = null;
        int worldX = worldLoc.getXPosition();
        int worldY = worldLoc.getYPosition();

        int screenX = worldX - gp.player.worldLoc.getXPosition() + gp.player.screenLoc.getXPosition();
        int screenY = worldY - gp.player.worldLoc.getYPosition() + gp.player.screenLoc.getYPosition();

        if (worldX + gp.tileSize > gp.player.worldLoc.getXPosition() - gp.player.screenLoc.getXPosition() &&
                worldX - gp.tileSize < gp.player.worldLoc.getXPosition() + gp.player.screenLoc.getXPosition() &&
                worldY + gp.tileSize > gp.player.worldLoc.getYPosition() - gp.player.screenLoc.getYPosition() &&
                worldY - gp.tileSize < gp.player.worldLoc.getYPosition() + gp.player.screenLoc.getYPosition()) {

            img = switch (direction) {
                case LU, U, RU -> (spriteNum == 1) ? up1 : (spriteNum == 2) ? up2 : null;
                case LD, D, RD -> (spriteNum == 1) ? down1 : (spriteNum == 2) ? down2 : null;
                case L -> (spriteNum == 1) ? left1 : (spriteNum == 2) ? left2 : null;
                case R -> (spriteNum == 1) ? right1 : (spriteNum == 2) ? right2 : null;
                default -> null;
            };

            if (dying) {
                img = die_image;
                dyingAnimation(g2d);
            }
            g2d.drawImage(img, screenX, screenY, gp.tileSize, gp.tileSize, null);
            //reset alpha
            changeAlpha(g2d, 1.0f);
        }
    }

    public void dyingAnimation(Graphics2D g2d) {
        dyingCounter++;

        if (dyingCounter <= 30) {
//            g2d.drawImage(die_image, worldLoc.getXPosition(), worldLoc.getYPosition(), null);
        } else {
            dying = false;
            alive = false;
            dyingCounter = 0;
        }
    }
}