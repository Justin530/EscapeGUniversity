package Entity.FlyingObject;

import Entity.*;
import Main.GamePanel;

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
            int monsterIndex = gp.collisionDetector.checkEntity(this, gp.monsters);
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

        switch (direction) {
            case U -> worldLoc.setYPosition(worldLoc.getYPosition() - speed);
            case D -> worldLoc.setYPosition(worldLoc.getYPosition() + speed);
            case L -> worldLoc.setXPosition(worldLoc.getXPosition() - speed);
            case R -> worldLoc.setXPosition(worldLoc.getXPosition() + speed);
            case LD -> {
                worldLoc.setXPosition(worldLoc.getXPosition() - speed);
                worldLoc.setYPosition(worldLoc.getYPosition() + speed);
            }
            case RD -> {
                worldLoc.setYPosition(worldLoc.getYPosition() + speed);
                worldLoc.setXPosition(worldLoc.getXPosition() + speed);
            }
            case LU -> {
                worldLoc.setXPosition(worldLoc.getXPosition() - speed);
                worldLoc.setYPosition(worldLoc.getYPosition() - speed);
            }
            case RU -> {
                worldLoc.setXPosition(worldLoc.getXPosition() + speed);
                worldLoc.setYPosition(worldLoc.getYPosition() - speed);
            }
        }

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
