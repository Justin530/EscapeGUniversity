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
    private Direction dir;
    private static final int BLOOD=1200;
    private int HP;//血量
    private int sign_weapon;//等于0是剑，等于1是火球
    private int r;//半径
    private int coldDown;//设定的冷却时间
    private int CD;//现在武器剩余的冷却时间
    private static final int BLOOD_LENGTH=40;//血条原长度
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        //the hitBox is smaller than the actual image, x and y values are calculated from the image
        hitBox = new Rectangle(loc.getXPosition(), loc.getYPosition(), gp.tileSize, gp.tileSize);
        hitBox.x = 8;//start from the corner of the image
        hitBox.y = 16;
        hitBox.width = 32;
        hitBox.height = 32;
        this.loc = new Location(100, 100);
        this.speed = 5;
        this.HP=BLOOD;
        this.coldDown = 10;
        this.r=1;
        setDefaultValues();
        getPlayerImage();
    }


    public void setDefaultValues(){
        if (this.loc == null) {
            this.loc = new Location(100, 100);
        } else {
            this.loc.setXPosition(100);
            this.loc.setYPosition(100);
        }
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

    public void update(){
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

        //check tile collision
        collisionOn = false;
        gp.collisionDetector.checkTile(this);

        //if collision is detected, player cannot move
        if (!collisionOn) {
            switch (direction) {
                case U -> loc.setYPosition(loc.getYPosition() - speed);
                case D -> loc.setYPosition(loc.getYPosition() + speed);
                case L -> loc.setXPosition(loc.getXPosition() - speed);
                case R -> loc.setXPosition(loc.getXPosition() + speed);
            }
        }

        if (spriteCount == 10) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCount = 0;
        } else {
            spriteCount++;
        }
    }

    /*
    * 多四个斜角方向
    * */
//    public void update() {
//        if (keyH.upPressed && keyH.leftPressed) {
//            dir = Direction.LU;
//            loc.setYPosition(loc.getYPosition() - (int) (speed * r * Math.cos(Math.toRadians(45))));
//            loc.setXPosition(loc.getXPosition() - (int) (speed * r * Math.cos(Math.toRadians(45))));
//        } else if (keyH.downPressed && keyH.leftPressed) {
//            loc.setYPosition(loc.getYPosition() + (int) (speed * r * Math.cos(Math.toRadians(45))));
//            loc.setXPosition(loc.getXPosition() - (int) (speed * r * Math.cos(Math.toRadians(45))));
//        } else if (keyH.rightPressed && keyH.upPressed) {
//            dir = Direction.RU;
//            loc.setXPosition(loc.getXPosition() + (int) (speed * r * Math.cos(Math.toRadians(45))));
//            loc.setYPosition(loc.getYPosition() - (int) (speed * r * Math.cos(Math.toRadians(45))));
//        } else if (keyH.rightPressed && keyH.downPressed) {
//            dir = Direction.RD;
//            loc.setXPosition(loc.getXPosition() + (int) (speed * r * Math.cos(Math.toRadians(45))));
//            loc.setYPosition(loc.getYPosition() + (int) (speed * r * Math.cos(Math.toRadians(45))));
//        } else if (keyH.upPressed) {
//            dir = Direction.U;
//            loc.setYPosition(loc.getYPosition() - speed);
//        } else if (keyH.downPressed) {
//            dir = Direction.D;
//            loc.setYPosition(loc.getYPosition() + speed);
//        } else if (keyH.leftPressed) {
//            dir = Direction.L;
//            loc.setXPosition(loc.getXPosition() - speed);
//        } else if (keyH.rightPressed) {
//            dir = Direction.R;
//            loc.setXPosition(loc.getXPosition() + speed);
//        } else if (keyH.attackPressed && this.getCD() == 0) {
//            this.setCD();
//        } else if (keyH.changeWeaponPressed) {
//            sign_weapon=(sign_weapon+1)%2;
//        } else if(keyH.greatPressed && this.getCD() == 0){
//            if(sign_weapon==1) {
//                //Fireball.greatPower();
//                this.setCD();
//            }
//        }
//
//    }
    public void draw(Graphics2D g2d) {
        BufferedImage img = switch (direction) {
            case U -> (spriteNum == 1) ? up1 : (spriteNum == 2) ? up2 : null;
            case D -> (spriteNum == 1) ? down1 : (spriteNum == 2) ? down2 : null;
            case L -> (spriteNum == 1) ? left1 : (spriteNum == 2) ? left2 : null;
            case R -> (spriteNum == 1) ? right1 : (spriteNum == 2) ? right2 : null;
            default -> null;
        };
        g2d.drawImage(img, loc.getXPosition(), loc.getYPosition(), gp.tileSize, gp.tileSize, null);
        //血条
        Color c = g2d.getColor();
        g2d.setColor(Color.RED);
        g2d.drawRect(loc.getXPosition() - 20, loc.getYPosition() - 40, BLOOD_LENGTH, 7);
        g2d.fillRect( loc.getXPosition()- 20, loc.getYPosition() - 40, HP, 7);
        g2d.setColor(c);
        //武器名
        if (sign_weapon == 0) {
            g2d.drawString("sword", loc.getXPosition() - 20, loc.getYPosition() - 45);
        } else {
            g2d.drawString("fireBoll", loc.getXPosition() - 20, loc.getYPosition() - 45);
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
    public void setCD() {
        CD = coldDown;
    }
}

