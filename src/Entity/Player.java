package Entity;

import Entity.FlyingObject.Bullet;
import Main.GamePanel;
import Main.KeyHandler;
import Main.UI.Message;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

public class Player extends Entity {
    KeyHandler keyH;
    public final Location screenLoc = new Location(0, 0);
    public int potionCount = 0;//number of potions
    private int weaponChange = 1;//长按时，只允许武器更换一次
    private int sign_weapon;//等于0是剑，等于1是火球
    private int fireBallNumber = 80;//火球的数量
    private int CD;//武器的冷却
    private static final int COLDTIME = 10;
    public int objectIndex = 999;

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

        getPlayerImage("/res/player/new_sword");
        getPlayerAttackImage("/res/player/new_sword");
    }


    public void setDefaultValues() {
        this.worldLoc.setXPosition(gp.tileSize * 4);
        this.worldLoc.setYPosition(gp.tileSize * 5);

        this.screenLoc.setXPosition(gp.screenWidth / 2 - gp.tileSize / 2);
        this.screenLoc.setYPosition(gp.screenHeight / 2 - gp.tileSize / 2);

        this.speed = 4;
        this.attack = 2;
        direction = Direction.D;

        //player status
        maxHP = 6;
        HP = maxHP;
        flyingObject = new Bullet(gp);
    }

    public void getPlayerImage(String parent) {
        up1 = setup(parent + File.separator + "up001.png", gp.tileSize, gp.tileSize);
        up2 = setup(parent + File.separator + "up002.png", gp.tileSize, gp.tileSize);
        down1 = setup(parent + File.separator + "down001.png", gp.tileSize, gp.tileSize);
        down2 = setup(parent + File.separator + "down002.png", gp.tileSize, gp.tileSize);
        right1 = setup(parent + File.separator + "right001.png", gp.tileSize, gp.tileSize);
        right2 = setup(parent + File.separator + "right002.png", gp.tileSize, gp.tileSize);
        left1 = setup(parent + File.separator + "left001.png", gp.tileSize, gp.tileSize);
        left2 = setup(parent + File.separator + "left002.png", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage(String parent) {
        attack_up1 = setup(parent + File.separator + "up_attack001.png", gp.tileSize, gp.tileSize * 2);
        attack_up2 = setup(parent + File.separator + "up_attack002.png", gp.tileSize, gp.tileSize * 2);
        attack_down1 = setup(parent + File.separator + "down_attack001.png", gp.tileSize, gp.tileSize * 2);
        attack_down2 = setup(parent + File.separator + "down_attack002.png", gp.tileSize, gp.tileSize * 2);
        attack_left1 = setup(parent + File.separator + "left_attack001.png", gp.tileSize * 2, gp.tileSize);
        attack_left2 = setup(parent + File.separator + "left_attack002.png", gp.tileSize * 2, gp.tileSize);
        attack_right1 = setup(parent + File.separator + "right_attack001.png", gp.tileSize * 2, gp.tileSize);
        attack_right2 = setup(parent + File.separator + "right_attack002.png", gp.tileSize * 2, gp.tileSize);

    }

    /**
     * 处理键盘的输入
     */
    public void update() {
        //check object collision
        objectIndex = gp.collisionDetector.checkObject(this, true);
        if (objectIndex != 999) {
//            Message message = new Message(gp, "Press F to interact");
//            gp.ui.addMessage(message);
            if (keyH.interactPressed){
//                System.out.println("interact");
                interactObject(objectIndex);
                //keyH.interactPressed = false;
            }
        }

        if (attacking) {
            attack();
        } else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.attackPressed) {
            if (keyH.upPressed) {
                direction = Direction.U;
            }
            if (keyH.downPressed) {
                direction = Direction.D;
            }
            if (keyH.leftPressed) {
                direction = Direction.L;
            }
            if (keyH.rightPressed) {
                direction = Direction.R;
            }
            if (keyH.upPressed && keyH.leftPressed) {
                direction = Direction.LU;
            }
            if (keyH.downPressed && keyH.leftPressed) {
                direction = Direction.LD;
            }
            if (keyH.rightPressed && keyH.upPressed) {
                direction = Direction.RU;
            }
            if (keyH.rightPressed && keyH.downPressed) {
                direction = Direction.RD;
            }
            if (keyH.attackPressed) {
                attacking = true;
            }

            //check tile collision
            collisionOn = false;
            gp.collisionDetector.checkTile(this);



            //check monster collision
            int monsterIndex = gp.collisionDetector.checkEntity(this, gp.monsters[gp.currentMap]);
            interactMonster(monsterIndex);

            //if collision is detected, player cannot move
            if (!collisionOn) {
                //移动时，根据direction变化x，y坐标
                move(this);
            }

            if (spriteCount == 10) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCount = 0;
            } else {
                spriteCount++;
            }
        }
        if (keyH.changeWeaponPressed && weaponChange == 1) {
            sign_weapon = (sign_weapon + 1) % 2;
            weaponChange = 0;
            //根据武器换图片
            if (sign_weapon == 0) {
                getPlayerImage("/res/player/new_sword");
                getPlayerAttackImage("/res/player/new_sword");
            } else {
                getPlayerImage("/res/player/new_gun");
                getPlayerAttackImage("/res/player/new_gun");
            }
        }
        //换武器
        if (!keyH.changeWeaponPressed) {
            weaponChange = 1;
        }
        //大招，往八个方向发射子弹
        if (keyH.greatPressed && this.getCD() == 0) {
            if (sign_weapon == 1 && fireBallNumber >= 8   ) {

                Bullet flyingObject1=new Bullet(gp);
                flyingObject1.set(worldLoc.getXPosition(), worldLoc.getYPosition(), Direction.D, true, this);
                gp.flyingObjectList.add(flyingObject1);
                Bullet flyingObject2=new Bullet(gp);
                flyingObject2.set(worldLoc.getXPosition(), worldLoc.getYPosition(), Direction.U, true, this);
                gp.flyingObjectList.add(flyingObject2);
                Bullet flyingObject3=new Bullet(gp);
                flyingObject3.set(worldLoc.getXPosition(), worldLoc.getYPosition(), Direction.L, true, this);
                gp.flyingObjectList.add(flyingObject3);
                Bullet flyingObject4=new Bullet(gp);
                flyingObject4.set(worldLoc.getXPosition(), worldLoc.getYPosition(), Direction.R, true, this);
                gp.flyingObjectList.add(flyingObject4);
                Bullet flyingObject5=new Bullet(gp);
                flyingObject5.set(worldLoc.getXPosition(), worldLoc.getYPosition(), Direction.RD, true, this);
                gp.flyingObjectList.add(flyingObject5);
                Bullet flyingObject6=new Bullet(gp);
                flyingObject6.set(worldLoc.getXPosition(), worldLoc.getYPosition(), Direction.RU, true, this);
                gp.flyingObjectList.add(flyingObject6);
                Bullet flyingObject7=new Bullet(gp);
                flyingObject7.set(worldLoc.getXPosition(), worldLoc.getYPosition(), Direction.LD, true, this);
                gp.flyingObjectList.add(flyingObject7);
                Bullet flyingObject8=new Bullet(gp);
                flyingObject8.set(worldLoc.getXPosition(), worldLoc.getYPosition(), Direction.LU, true, this);
                gp.flyingObjectList.add(flyingObject8);
                shotAvailableCounter = 0;
                fireBallNumber -= 8;
            }
            this.setCD();
        }
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter >= 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }

        if (HP <= 0) {
            gp.gameState = gp.gameOverState;
        }
    }

    public void interactMonster(int i) {
        if (i != 999) {
            if (!invincible && !gp.monsters[gp.currentMap][i].dying) {
                HP--;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i, int attack) {
        if (i != 999) {
            if (!gp.monsters[gp.currentMap][i].invincible) {
                gp.monsters[gp.currentMap][i].HP -= attack;
                gp.ui.addMessage("-" + attack);

                gp.monsters[gp.currentMap][i].invincible = true;
                gp.monsters[gp.currentMap][i].damageReaction();

                if (gp.monsters[gp.currentMap][i].HP <= 0) {
                    gp.monsters[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("killed the " + gp.monsters[gp.currentMap][i].name);
                }
            }
        }
    }

    public void attack() {
        if (sign_weapon == 0) {
            //近战武器
            spriteCount++;
            if (spriteCount <= 5) {
                spriteNum = 1;
            } else if (spriteCount <= 25) {
                spriteNum = 2;
                //save the current location and hitBox of the player
                int currentWorldX = worldLoc.getXPosition();
                int currentWorldY = worldLoc.getYPosition();
                int hitBoxWidth = hitBox.width;
                int hitBoxHeight = hitBox.height;
                //change the location and hitBox of the player
                switch (direction) {
                    case U -> worldLoc.setYPosition(worldLoc.getYPosition() - attackBox.height);

                    case D -> worldLoc.setYPosition(worldLoc.getYPosition() + attackBox.height);

                    case L -> worldLoc.setXPosition(worldLoc.getXPosition() - attackBox.width);

                    case R -> worldLoc.setXPosition(worldLoc.getXPosition() + attackBox.width);
                }
                //attackBox becomes the hitBox
                hitBox.width = attackBox.width;
                hitBox.height = attackBox.height;
                //check monster collision with updated hitBox and worldLoc
                int monsterIndex = gp.collisionDetector.checkEntity(this, gp.monsters[gp.currentMap]);
                damageMonster(monsterIndex, attack);
                if (monsterIndex == 999 && spriteCount == 25) {
                    gp.ui.addMessage("Miss!");
                }

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
        } else {
            //子弹远程攻击
            if (CD==0&&fireBallNumber > 0 && !flyingObject.alive  ) {
                //set default coordinates, direction and user
                flyingObject.set(worldLoc.getXPosition(), worldLoc.getYPosition(), direction, true, this);
                //add it to the list
                gp.flyingObjectList.add(flyingObject);
                shotAvailableCounter = 0;
                fireBallNumber--;
                setCD();
            }else {
                attacking = false;
            }
        }
    }

    public void interactObject(int i) {
        if (Objects.equals(gp.objects[gp.currentMap][i].name, "Potion")) {
//            System.out.println("potion");
            potionCount++;
            gp.objects[gp.currentMap][i].interact();
            gp.objects[gp.currentMap][i] = null;
        }
        else if (Objects.equals(gp.objects[gp.currentMap][i].name, "Board")) {
            gp.gameState = gp.dialogueState;
            gp.objects[gp.currentMap][i].interact();
        }
    }

    /**
     * 绘画血条、人物移动等
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
        }

        /*if (screenLoc.getXPosition() > worldLoc.getXPosition()) {
            x = worldLoc.getXPosition();
        }
        if (screenLoc.getYPosition() > worldLoc.getYPosition()) {
            y = worldLoc.getYPosition();
        }
        if (gp.screenWidth - screenLoc.getXPosition() > gp.worldWidth - worldLoc.getXPosition()) {
            x = gp.screenWidth - gp.worldWidth + worldLoc.getXPosition();
        }
        if (gp.screenHeight - screenLoc.getYPosition() > gp.worldHeight - worldLoc.getYPosition()) {
            y = gp.screenHeight - gp.worldHeight + worldLoc.getYPosition();
        }*/

        if (invincible) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }

        g2d.drawImage(img, x, y, null);
        //reset alpha
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        //加粗字体，美化一下
        Font ori = g2d.getFont();
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 12));
        //武器名
        if (sign_weapon == 0) {
            g2d.drawString("sword", screenLoc.getXPosition() + 7, screenLoc.getYPosition() - 4);
        } else {
            g2d.drawString("Bullet: " + fireBallNumber, screenLoc.getXPosition() - 7, screenLoc.getYPosition() - 4);
        }

        //飞行物还存在时，打印：正在装弹
        Color customColor = new Color(200, 100, 5);//设置颜色
        g2d.setColor(customColor);
        if(flyingObject.alive){
            g2d.drawString("正在装弹", screenLoc.getXPosition() , screenLoc.getYPosition() - 18);
        }
        g2d.setFont(ori);
        //减武器CD
        if (CD > 0) CD--;
    }

    public int getCD() {
        return CD;
    }

    public void setCD() {
        this.CD = COLDTIME;
    }
}

