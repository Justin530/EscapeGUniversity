package Entity.Monsters;

import Entity.FlyingObject.Rock;
import Main.GamePanel;
import Entity.*;
import AI.*;

import java.util.Random;

public class Rajiang extends Entity{

    public Rajiang(GamePanel gp) {
        super(gp);
        type = 1;
        name = "Rajiang";
        maxHP = 8;
        HP = maxHP;
        flyingObject = new Rock(gp);

        hitBox.x = 8;//start from the corner of the image
        hitBox.y = 16;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        hitBox.width = 32;
        hitBox.height = 32;

        direction = Direction.D;
        speed = 1;
        onPath = false;

        getRajiangImage();
    }

    public void getRajiangImage() {
        up1 = setup("/res/Monster/Monster_U.png", gp.tileSize, gp.tileSize);
        up2 = setup("/res/Monster/Monster_Uw.png", gp.tileSize, gp.tileSize);
        down1 = setup("/res/Monster/Monster_D.png", gp.tileSize, gp.tileSize);
        down2 = setup("/res/Monster/Monster_Dw.png", gp.tileSize, gp.tileSize);
        right1 = setup("/res/Monster/Monster_R.png", gp.tileSize, gp.tileSize);
        right2 = setup("/res/Monster/Monster_Rw.png", gp.tileSize, gp.tileSize);
        left1 = setup("/res/Monster/Monster_L.png", gp.tileSize, gp.tileSize);
        left2 = setup("/res/Monster/Monster_Lw.png", gp.tileSize, gp.tileSize);
    }

    public void update() {
        super.update();
        if (shotAvailableCounter < 30) {
            shotAvailableCounter ++;
        }

        int xDistance = Math.abs(worldLoc.getXPosition() - gp.player.worldLoc.getXPosition());
        int yDistance = Math.abs(worldLoc.getYPosition() - gp.player.worldLoc.getYPosition());
        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if (!onPath && tileDistance <= 5) {
            int i = new Random().nextInt(100) + 1;
            if (i > 50) {
                onPath = true;
//                System.out.println("Rajiang is Angry!");
                gp.ui.addMessage("Rajiang is Angry!");
            }
        }
        if (onPath && tileDistance > 20) {
            onPath = false;
//            System.out.println("Rajiang is Calm!");
            gp.ui.addMessage("Rajiang is Calm!");
        }
    }

    public void setAction() {
        if (onPath) {
            int goalCol = (gp.player.worldLoc.getXPosition() + gp.player.hitBox.x) / gp.tileSize;
            int goalRow = (gp.player.worldLoc.getYPosition() + gp.player.hitBox.y) / gp.tileSize;

            int i = new Random().nextInt(100) + 1;
            if (i > 99 && !flyingObject.alive && shotAvailableCounter == 30) {
                flyingObject.set(worldLoc.getXPosition(), worldLoc.getYPosition(), direction, true, this);
                gp.flyingObjectList.add(flyingObject);
                shotAvailableCounter = 0;
            }

            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter ++;
            Random random = new Random();

            if (actionLockCounter == 120) {
                actionLockCounter = 0;
                //randomly choose a direction
                int rand = random.nextInt(100) + 1;//1~100
                if (rand <= 25) {
                    direction = Direction.L;
                } else if (rand <= 50) {
                    direction = Direction.R;
                } else if (rand <= 75) {
                    direction = Direction.U;
                } else {
                    direction = Direction.D;
                }
            }

        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true;//if player attacks, then the monster will chase the player
    }
}
