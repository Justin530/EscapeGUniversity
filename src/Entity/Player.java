package Entity;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    KeyHandler keyH;
    public final Location screenLoc = new Location(0, 0);
    private int sign_weapon;//等于0是剑，等于1是火球
    private int coldDown;//设定的冷却时间
    private int CD;//现在武器剩余的冷却时间
    private int onAttackState;//受攻击的间隔

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        setDefaultValues();

        //the hitBox is smaller than the actual image, x and y values are calculated from the image
        hitBox = new Rectangle(worldLoc.getXPosition(), worldLoc.getYPosition(), gp.tileSize, gp.tileSize);
        hitBox.x = 8;//start from the corner of the image
        hitBox.y = 16;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        hitBox.width = 32;
        hitBox.height = 32;

        attackBox.width = 36;
        attackBox.height = 36;

        getPlayerImage();
        getPlayerAttackImage();
    }


    public void setDefaultValues(){
        this.worldLoc.setXPosition(gp.tileSize * 14);
        this.worldLoc.setYPosition(gp.tileSize * 33);

        this.screenLoc.setXPosition(gp.screenWidth / 2 - gp.tileSize / 2);
        this.screenLoc.setYPosition(gp.screenHeight / 2 - gp.tileSize / 2);

        this.speed = 4;
        direction = Direction.D;

        //player status
        maxHP = 6;
        HP = maxHP;
    }

    public void getPlayerImage() {
        up1 = setup("/res/player/boy_up_1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/res/player/boy_up_2.png", gp.tileSize, gp.tileSize);
        down1 = setup("/res/player/boy_down_1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/res/player/boy_down_2.png", gp.tileSize, gp.tileSize);
        right1 = setup("/res/player/boy_right_1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/res/player/boy_right_2.png", gp.tileSize, gp.tileSize);
        left1 = setup("/res/player/boy_left_1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/res/player/boy_left_2.png", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage() {
        attack_up1 = setup("/res/player/boy_attack_up_1.png", gp.tileSize, gp.tileSize * 2);
        attack_up2 = setup("/res/player/boy_attack_up_2.png", gp.tileSize, gp.tileSize * 2);
        attack_down1 = setup("/res/player/boy_attack_down_1.png", gp.tileSize, gp.tileSize * 2);
        attack_down2 = setup("/res/player/boy_attack_down_2.png", gp.tileSize, gp.tileSize * 2);
        attack_left1 = setup("/res/player/boy_attack_left_1.png", gp.tileSize * 2, gp.tileSize);
        attack_left2 = setup("/res/player/boy_attack_left_2.png", gp.tileSize * 2, gp.tileSize);
        attack_right1 = setup("/res/player/boy_attack_right_1.png", gp.tileSize * 2, gp.tileSize);
        attack_right2 = setup("/res/player/boy_attack_right_2.png", gp.tileSize * 2, gp.tileSize);
    }

    /**
     * 处理键盘的输入
     */
    public void update(){
        if (attacking) {
            attack();
        }
        else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.attackPressed) {
            if(keyH.upPressed){
                direction = Direction.U;
            }
            if(keyH.downPressed){
                direction = Direction.D;
            }
            if(keyH.leftPressed){
                direction = Direction.L;
            }
            if(keyH.rightPressed){
                direction = Direction.R;
            }
            if(keyH.upPressed && keyH.leftPressed){
                direction = Direction.LU;
            }
            if(keyH.downPressed && keyH.leftPressed){
                direction = Direction.LD;
            }
            if(keyH.rightPressed && keyH.upPressed){
                direction = Direction.RU;
            }
            if(keyH.rightPressed && keyH.downPressed){
                direction = Direction.RD;
            }
            if (keyH.attackPressed) {
                attacking = true;
            } else if (keyH.changeWeaponPressed) {
                sign_weapon=(sign_weapon+1)%2;
            } else if(keyH.greatPressed && this.getCD() == 0){
                if(sign_weapon==1) {
                    //Fireball.greatPower();
                    this.setCD();}
            }

            //check tile collision
            collisionOn = false;
            gp.collisionDetector.checkTile(this);

            //check monster collision
            int monsterIndex = gp.collisionDetector.checkEntity(this, gp.monsters);
            interactMonster(monsterIndex);

            //if collision is detected, player cannot move
            if (!collisionOn) {
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
            }

            if (spriteCount == 10) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCount = 0;
            } else {
                spriteCount++;
            }
        }

        if (invincible) {
            invincibleCounter ++;
            if (invincibleCounter >= 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void interactMonster(int i) {
        if (i != 999) {
            if (!invincible) {
                HP --;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i) {
        if (i != 999) {
            if (!gp.monsters[i].invincible) {
                gp.monsters[i].HP --;
                gp.monsters[i].invincible = true;
                if (gp.monsters[i].HP <= 0) {
                    gp.monsters[i].dying = true;
                }
            }
        }
    }

    /**
     * 绘画血条、人物移动等
     * @param g2d
     */
    public void draw(Graphics2D g2d) {
        int x = screenLoc.getXPosition(), y = screenLoc.getYPosition();
        BufferedImage img = null;
        switch (direction) {
            case LU, U, RU -> {
                if (attacking) {
                    y -= gp.tileSize;
                    if (spriteNum == 1) img = attack_up1;
                    else if (spriteNum == 2) img = attack_up2;
                } else {
                    if (spriteNum == 1) img = up1;
                    else if (spriteNum == 2) img = up2;
                }
            }
            case LD, D, RD -> {
                if (attacking) {
                    if (spriteNum == 1) img = attack_down1;
                    else if (spriteNum == 2) img = attack_down2;
                } else {
                    if (spriteNum == 1) img = down1;
                    else if (spriteNum == 2) img = down2;
                }
            }
            case L -> {
                if (attacking) {
                    x -= gp.tileSize;
                    if (spriteNum == 1) img = attack_left1;
                    else if (spriteNum == 2) img = attack_left2;
                } else {
                    if (spriteNum == 1) img = left1;
                    else if (spriteNum == 2) img = left2;
                }
            }
            case R -> {
                if (attacking) {
                    if (spriteNum == 1) img = attack_right1;
                    else if (spriteNum == 2) img = attack_right2;
                } else {
                    if (spriteNum == 1) img = right1;
                    else if (spriteNum == 2) img = right2;
                }
            }
        };

        if (screenLoc.getXPosition() > worldLoc.getXPosition()) {
            x = worldLoc.getXPosition();
        }
        if (screenLoc.getYPosition() > worldLoc.getYPosition()) {
            y  = worldLoc.getYPosition();
        }
        if (gp.screenWidth - screenLoc.getXPosition() > gp.worldWidth - worldLoc.getXPosition()) {
            x = gp.screenWidth - gp.worldWidth + worldLoc.getXPosition();
        }
        if (gp.screenHeight - screenLoc.getYPosition() > gp.worldHeight - worldLoc.getYPosition()) {
            y = gp.screenHeight - gp.worldHeight + worldLoc.getYPosition();
        }

        if (invincible) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }

        g2d.drawImage(img, x, y,null);
        //reset alpha
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    /**
     * 攻击的模块
     */
    public void attack(){
        spriteCount++;

        if (spriteCount <= 5) {
            spriteNum = 1;
        }
        else if (spriteCount <= 25) {
            spriteNum = 2;
            //save the current location and hitBox of the player
            int currentWorldX = worldLoc.getXPosition();
            int currentWorldY = worldLoc.getYPosition();
            int hitBoxWidth = hitBox.width;
            int hitBoxHeight = hitBox.height;
            //change the location and hitBox of the player
            switch (direction) {
                case U -> {
                    worldLoc.setYPosition(worldLoc.getYPosition() - attackBox.height);
                }
                case D -> {
                    worldLoc.setYPosition(worldLoc.getYPosition() + attackBox.height);
                }
                case L -> {
                    worldLoc.setXPosition(worldLoc.getXPosition() - attackBox.width);
                }
                case R -> {
                    worldLoc.setXPosition(worldLoc.getXPosition() + attackBox.width);
                }
            }
            //attackBox becomes the hitBox
            hitBox.width = attackBox.width;
            hitBox.height = attackBox.height;
            //check monster collision with updated hitBox and worldLoc
            int monsterIndex = gp.collisionDetector.checkEntity(this, gp.monsters);
            damageMonster(monsterIndex);

            //change the location and hitBox back
            worldLoc.setXPosition(currentWorldX);
            worldLoc.setYPosition(currentWorldY);
            hitBox.width = hitBoxWidth;
            hitBox.height = hitBoxHeight;
        }
        else {
            spriteNum = 1;
            spriteCount = 0;
            attacking = false;
        }
    }

    /**
     * @return CD
     * 返回现在的冷却时间
     */
    public int getCD() {
        return this.CD;
    }

    /**
     * 进入CD
     */
    public void setCD() {CD = coldDown;}

    public int getHP() {return HP;}

    public void setHP(int HP) {this.HP = HP;}

    public int getSign_weapon() {return sign_weapon;}

    public int getColdDown() {return coldDown;}

    public int getOnAttackState() {return onAttackState;}

}

