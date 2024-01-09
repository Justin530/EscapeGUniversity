package Entity.FlyingObject;

import Entity.*;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FlyingObject  extends Entity {
    Entity user;

    public FlyingObject(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, Direction direction, boolean alive, Entity user) {
        this.worldLoc.setXPosition(worldX);
        this.worldLoc.setYPosition(worldY);
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.HP = maxHP;
    }

    public void update() {
        if (user == gp.player){
            int monsterIndex = gp.collisionDetector.checkEntity(this, gp.monsters[gp.currentMap]);
            if (monsterIndex != 999) {
                gp.player.damageMonster(monsterIndex, attack);
                alive = false;
            }
        } else {
            boolean contactPlayer = gp.collisionDetector.checkPlayer(this);
            if (contactPlayer && !gp.player.invincible) {
                damagePlayer(attack);
                alive = false;
            }
        }
        //移动时，根据direction变化x，y坐标
        move(this);

        HP --;
        if (HP <= 0) {
            alive = false;
        }

        spriteCount ++;
        if (spriteCount == 10) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCount = 0;
        }
    }
}
