package Entity;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    public final Location screenLoc = new Location(0, 0);
    private static final int BLOOD=1200;
    private int HP;//血量
    private int sign_weapon;//等于0是剑，等于1是火球
    private int coldDown;//设定的冷却时间
    private int CD;//现在武器剩余的冷却时间
    private static final int BLOOD_LENGTH=40;//血条原长度
    private int onAttackState;//受攻击的间隔

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();

        //the hitBox is smaller than the actual image, x and y values are calculated from the image
        hitBox = new Rectangle(worldLoc.getXPosition(), worldLoc.getYPosition(), gp.tileSize, gp.tileSize);
        hitBox.x = 8;//start from the corner of the image
        hitBox.y = 16;
        hitBox.width = 32;
        hitBox.height = 32;
        this.HP=BLOOD;
        this.coldDown = 10;

        getPlayerImage();
    }


    public void setDefaultValues(){
        this.worldLoc.setXPosition(gp.tileSize * 14);
        this.worldLoc.setYPosition(gp.tileSize * 33);

        this.screenLoc.setXPosition(gp.screenWidth / 2 - gp.tileSize / 2);
        this.screenLoc.setYPosition(gp.screenHeight / 2 - gp.tileSize / 2);

        this.speed = 4;
        direction = Direction.D;
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_down_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_right_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_left_2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理键盘的输入
     */
    public void update(){
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
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
            if (keyH.attackPressed && this.getCD() == 0) {
                this.setCD();
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
        }

        if (spriteCount == 10) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCount = 0;
        } else {
            spriteCount++;
        }
    }

    /**
     * 绘画血条、人物移动等
     * @param g2d
     */
    public void draw(Graphics2D g2d) {
        BufferedImage img = switch (direction) {
            case LU, U, RU -> (spriteNum == 1) ? up1 : (spriteNum == 2) ? up2 : null;
            case LD, D, RD -> (spriteNum == 1) ? down1 : (spriteNum == 2) ? down2 : null;
            case L -> (spriteNum == 1) ? left1 : (spriteNum == 2) ? left2 : null;
            case R -> (spriteNum == 1) ? right1 : (spriteNum == 2) ? right2 : null;
            default -> null;
        };
        g2d.drawImage(img, screenLoc.getXPosition(), screenLoc.getYPosition(), gp.tileSize, gp.tileSize, null);
        //武器名
        if (sign_weapon == 0) {
            g2d.drawString("sword", worldLoc.getXPosition() - 20, worldLoc.getYPosition() - 45);
        } else {
            g2d.drawString("fireBoll", worldLoc.getXPosition() - 20, worldLoc.getYPosition() - 45);
        }

    }

    /**
     * 攻击的模块
     */
    public void attack(){
        if(getHP() <= 0 ) return;
        this.onAttackState = 5;
        if(getHP() <= 0) {
            this.setHP(0);
            return;
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

