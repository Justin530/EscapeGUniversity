package Entity;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Ball extends Entity implements Runnable{
    private int x;
    private int y;
    private Location loc;
    private int speed;
    private int coldDownTime;
    private int damage;
    private Direction dir;
    boolean isLive = true;  //子弹是否还存活，以判断是否还继续绘制子弹
    private Image img;

    public Ball(GamePanel gp, Location loc, int speed, int coldDownTime, int damage, Direction dir) {
        super(gp);
        this.x = loc.getXPosition();
        this.y = loc.getYPosition();
        this.loc = loc;
        this.speed = speed;
        this.coldDownTime = coldDownTime;
        this.damage = damage;
        this.dir = dir;
    }

    public static Image getImage(String path){
        URL u = Ball.class.getClassLoader().getResource(path);
        BufferedImage img = null;
        try {
            img = ImageIO.read(u);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    public void run(){
        while(true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch (dir){
                case L:
                    x+=speed*dir.getDx();
                    break;
                case R:
                    x-=speed*dir.getDx();
                    break;
                case U:
                    y+=speed*dir.getDy();
                    break;
                case D:
                    y-=speed*dir.getDy();
                    break;
                case LU:
                    x+=speed*dir.getDx();
                    y+=speed*dir.getDy();
                    break;
                case LD:
                    x+=speed*dir.getDx();
                    y-=speed*dir.getDy();
                    break;
                case RU:
                    x-=speed*dir.getDx();
                    y+=speed*dir.getDy();
                    break;
                case RD:
                    x-=speed*dir.getDx();
                    y-=speed*dir.getDy();
                    break;
            }
            loc.setXPosition(x);
            loc.setYPosition(y);
            //每移动一步，判断子弹是否还能再此方向上继续移动
            if(!Location.canMove(this,dir)){
                isLive = false;
                break;
            }
            //判断子弹是否越出边界
            if(!(x>=0&&x<=768&&y>=0&&y<=576)){
                isLive = false;
                break;
            }
        }
    }

    public void draw(Graphics2D g2d){
        Image img = getImage("rock_down_1.png");
        g2d.drawImage(img, loc.getXPosition(), loc.getYPosition(), gp.tileSize, gp.tileSize, null);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Location getLoc() {
        return loc;
    }
    public int getSpeed() {
        return speed;
    }
    public int getColdDownTime() {
        return coldDownTime;
    }
    public int getDamage() {
        return damage;
    }
    public Direction getDir() {
        return dir;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void setColdDownTime(int coldDownTime) {
        this.coldDownTime = coldDownTime;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
}
