package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import Entity.Direction;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        this.loc = new Location(0, 0);
        this.speed = 5;
    }

    public void setDefaultValues(){
        this.loc.setxPosition(100);
        this.loc.setyPosition(100);
        this.speed = 4;
    }

    public void update(){
        if(keyH.upPressed){
            loc.setyPosition(loc.getyPosition()-speed);
        }
        if(keyH.downPressed){
            loc.setyPosition(loc.getyPosition()+speed);
        }
        if(keyH.leftPressed){
            loc.setxPosition(loc.getxPosition()-speed);
        }
        if(keyH.rightPressed){
            loc.setxPosition(loc.getxPosition()+speed);
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillRect(loc.getxPosition(), loc.getyPosition(), gp.tileSize, gp.tileSize);
        //The pictures should be saved as .png files!
    }
    private int HP;//血量
    private int sign_weapon;//等于0是剑，等于1是火球
    private Direction dir;//表示英雄的方向
    private int player;//等于1是P1，等于2是P2
    private int x,y;//坐标
    private int r;//半径
    private int speed;//运动速度
    private int coldDown;//设定的冷却时间
    private  int CD;//现在武器剩余的冷却时间
    private int[] keys;
    private boolean bL=false, bU=false, bR=false, bD=false;
    public Player(int x, int y, int HP,int player,int speed,int r) {
        this.x=x;
        this.y=y;
        this.HP=HP;
        this.player=player;
        this.speed=speed;
        this.r=r;
        keys = new int[7];
        if(player == 2){
            keys[0] = KeyEvent.VK_LEFT;
            keys[1] = KeyEvent.VK_UP;
            keys[2] = KeyEvent.VK_RIGHT;
            keys[3] = KeyEvent.VK_DOWN;
            keys[4] = KeyEvent.VK_NUMPAD1;
            keys[5] = KeyEvent.VK_NUMPAD2;
            keys[6] = KeyEvent.VK_NUMPAD3;
        } else {
            keys[0] = KeyEvent.VK_A;
            keys[1] = KeyEvent.VK_W;
            keys[2] = KeyEvent.VK_D;
            keys[3] = KeyEvent.VK_S;
            keys[4] = KeyEvent.VK_J;
            keys[5] = KeyEvent.VK_K;
            keys[6] = KeyEvent.VK_L;
        }
    }

    public void KeyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == keys[0]) dir=Direction.L;
        else if(key == keys[1]) dir = Direction.U;
        else if(key == keys[2]) dir = Direction.R;
        else if(key == keys[3]) dir = Direction.D;
        else if(key == keys[4] && this.getCD() == 0) {
            //this.setState();
            this.setCD();
        } else if(key == keys[5]){
            sign_weapon=(sign_weapon+1)%2;
        } else if(key == keys[6] && this.getCD() == 0){
            if(sign_weapon==1) {
                ((Fireball) this.getCurrentWeapon()).setUltimateState();
                this.getCurrentWeapon().setColdDown();
            }
        }
        locateDirection();
    }

    /**
     * @return CD
     * 返回现在的冷却时间
     */
    public int getCD(){
        return this.CD;
    }


    public void setCD(){
        CD=coldDown;
    }
    public void locateDirection() {
        if(bL && !bU && !bR && !bD) dir = Direction.L;
        else if(bL && bU && !bR && !bD) dir = Direction.LU;
        else if(!bL && bU && !bR && !bD) dir = Direction.U;
        else if(!bL && bU && bR && !bD) dir = Direction.RU;
        else if(!bL && !bU && bR && !bD) dir = Direction.R;
        else if(!bL && !bU && bR && bD) dir = Direction.RD;
        else if(!bL && !bU && !bR && bD) dir = Direction.D;
        else if(bL && !bU && !bR && bD) dir = Direction.LD;
    }
}

