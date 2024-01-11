package Entity.Monsters;

import AI.QuickPathFinder;
import Entity.Entity;
import Entity.FlyingObject.GhostBall;
import Entity.Direction;
import Entity.Location;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import Object.OBJ_Key;
import static Entity.Direction.*;

public class Yukari extends Entity {
    private Random pController;
    public static int attackIntervalCounter = 0;
    public static int bulletCounter = 0;
    public static Direction[] hachiDirs = {L,LU,U,RU,R,RD,D,LD};
    public static GhostBall[] balls = new GhostBall[8];
    public Yukari(GamePanel gp) {
        super(gp);
        type = 1;
        name = "Yukari";
        maxHP = 24;
        HP = maxHP;
        for(int i = 0 ; i < 8; i++){
            balls[i] = new GhostBall(gp);
            balls[i].set(worldLoc.getXPosition(), worldLoc.getYPosition(), hachiDirs[i], true, this);
        }

        hitBox.x = 0;//从角落开始
        hitBox.y = 0;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        hitBox.width = 60;
        hitBox.height = 120;

        direction = Direction.D;
        speed = 0;
        onPath = true;
        pController = new Random(System.currentTimeMillis());
        getMonsterImage();
    }
    public void getMonsterImage() {
        up1 = setup("/res/ghost/Ghost-up.png", gp.tileSize, gp.tileSize);
        up2 = setup("/res/ghost/Ghost-up.png", gp.tileSize, gp.tileSize);
        down1 = setup("/res/ghost/Ghost-down.png", gp.tileSize, gp.tileSize);
        down2 = setup("/res/ghost/Ghost-down.png", gp.tileSize, gp.tileSize);
        right1 = setup("/res/ghost/Ghost-right.png", gp.tileSize, gp.tileSize);
        right2 = setup("/res/ghost/Ghost-right.png", gp.tileSize, gp.tileSize);
        left1 = setup("/res/ghost/Ghost-left.png", gp.tileSize, gp.tileSize);
        left2 = setup("/res/ghost/Ghost-left.png", gp.tileSize, gp.tileSize);
    }
    @Override
    public void update() {
        hpBarOn = true;
        super.update();
        if (shotAvailableCounter < 30) {
            shotAvailableCounter ++;
        }

        int xDistance = Math.abs(worldLoc.getXPosition() - gp.player.worldLoc.getXPosition());
        int yDistance = Math.abs(worldLoc.getYPosition() - gp.player.worldLoc.getYPosition());
        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if (!onPath && tileDistance >10) {
                onPath = true;
        }else if (onPath && tileDistance <=5) {
            onPath = false;
        }
    }
    @Override
    public void setAction(){
        if(attackIntervalCounter<=300) {
            if(bulletCounter<=210) {
                //flyingObject.set(worldLoc.getXPosition(), worldLoc.getYPosition(), hachiDirs[attackIntervalCounter / 60], true, this);
                //gp.flyingObjectList.add(flyingObject);
                if(bulletCounter%30==0) {
                    balls[bulletCounter / 30].set(worldLoc.getXPosition() + 2*48 * balls[bulletCounter / 30].direction.getDx(), worldLoc.getYPosition() + 2*48 * balls[bulletCounter / 30].direction.getDy(), hachiDirs[bulletCounter / 30], true, this);
                    gp.flyingObjectList.add(balls[bulletCounter / 30]);
                }
                attackIntervalCounter ++;
                bulletCounter ++;
            }
            else{
                attackIntervalCounter ++;
            }
        }else {
            attackIntervalCounter = 0;
            bulletCounter = 0;
        }
        if (onPath) {
            direction = QuickPathFinder.findNextStep(this,this.gp.player.worldLoc);
        } else {
            actionLockCounter ++;
            if (actionLockCounter == 60) {
                actionLockCounter = 0;
                //randomly choose a direction
                if(pController.nextInt(4) == 2)
                    direction = Direction.getRandomDirection();
            }

        }
    }
    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true;//if player attacks, then the monster will chase the player
    }
@Override
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
            //monster HP bar
            if (type == 1 && hpBarOn) {
                double oneScale = (double) gp.tileSize / maxHP;
                double hpBarScale = 10*oneScale * HP;

                g2d.setColor(new Color(0, 0, 0, 255));
                g2d.fillRect(148, 520, (int) (10*oneScale*this.maxHP), 10);

                g2d.setColor(new Color(142, 8, 8, 250));
                g2d.fillRect(148, 520, (int) hpBarScale, 10);

                hpBarCounter++;
                if (hpBarCounter >= 600) {
                    hpBarOn = false;
                    hpBarCounter = 0;
                }
            }

            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2d, 0.5f);
            }
            if (dying) {
                dyingAnimation(g2d);
            }
            g2d.drawImage(img, screenX-gp.tileSize, screenY-gp.tileSize, gp.tileSize*3, gp.tileSize*3, null);
            //reset alpha
            changeAlpha(g2d, 1.0f);
        }
    }
    public void dropObject(){
        gp.objects[0][3] = new OBJ_Key(gp);
        gp.objects[0][3].worldLoc = new Location(this.worldLoc.getXPosition(),this.worldLoc.getYPosition());
    }
}
