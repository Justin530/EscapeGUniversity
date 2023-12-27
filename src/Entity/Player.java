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

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        hitBox = new Rectangle(loc.getxPosition(), loc.getyPosition(), gp.tileSize, gp.tileSize);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        if (this.loc == null) {
            this.loc = new Location(100, 100);
        } else {
            this.loc.setxPosition(100);
            this.loc.setyPosition(100);
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
            loc.setyPosition(loc.getyPosition()-speed);
        }
        if(keyH.downPressed){
            direction = Direction.D;
            loc.setyPosition(loc.getyPosition()+speed);
        }
        if(keyH.leftPressed){
            direction = Direction.L;
            loc.setxPosition(loc.getxPosition()-speed);
        }
        if(keyH.rightPressed){
            direction = Direction.R;
            loc.setxPosition(loc.getxPosition()+speed);
        }

        if (spriteCount == 10) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCount = 0;
        } else {
            spriteCount++;
        }
    }

    public void draw(Graphics2D g2d) {
        BufferedImage img = switch (direction) {
            case U -> (spriteNum == 1) ? up1 : (spriteNum == 2) ? up2 : null;
            case D -> (spriteNum == 1) ? down1 : (spriteNum == 2) ? down2 : null;
            case L -> (spriteNum == 1) ? left1 : (spriteNum == 2) ? left2 : null;
            case R -> (spriteNum == 1) ? right1 : (spriteNum == 2) ? right2 : null;
            default -> null;
        };

        g2d.drawImage(img, loc.getxPosition(), loc.getyPosition(), gp.tileSize, gp.tileSize, null);
    }
}

