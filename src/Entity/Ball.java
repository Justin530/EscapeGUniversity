package Entity;

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
    private Image []img;

    public Ball(Location loc, int speed, int coldDownTime, int damage, Direction dir) {
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
            if(!(x>=0&&x<=768&&y>=0&&y<=576)){
                break;
            }
        }
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
