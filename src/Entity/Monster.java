package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
暂时考虑每个怪物单独对应一个线程，线程负责更新怪物状态并作出行为

一些方法只做了近战的（zombie的），ghost需要重写

需要的信息由gamePanel提供

当前需要且可以完成的方法

updateStatus()

findPath()

run()

attack()
 */
public class Monster extends Entity implements Runnable {
    private int HP;
    Player player = gamePanel.player;
    private enum Status {
        Chasing,Waiting,Attacking,OnAttack,Dead;
    }
    public Status monsterStatus = Status.Chasing;
    Monster(){
    }


    /**
     * 此类储存寻路中下一步的信息
     */
    private class NextStep{
        Location currentLoc;   //当前节点位置
        Location destination;  //目标位置
        Direction direction;   //当前节点的方向（相对怪物）
        int G ;
        int F ;
        int H;

        boolean notMeeting = true;//当notMeeting == False 时标志找到玩家，需要更好的检测方法
        NextStep(Location currentLoc,Location destination,Direction dir,boolean notMeeting){
            this.destination = destination;
            this.currentLoc = currentLoc;
            this.direction = dir;
            this.notMeeting = notMeeting;
            //G = 10 或 14 使用勾股定理计算
            G =  (int) (10*Math.sqrt(direction.getDx()*direction.getDx()+direction.getDy()*direction.getDy()));
            //计算曼哈顿距离
            H = Math.abs(currentLoc.getXPosition()-destination.getXPosition())+Math.abs(currentLoc.getYPosition()-destination.getYPosition());
            F = G+H;
        }
    }

    /**
     * A*算法寻路，G=10、14，H=曼哈顿距离
     * @return
     */
    public NextStep findPath(Location playerlocation){

        return null;
    }

    /**
     *根据怪物状态控制其行为的线程run()方法，但目前只实现了zombie的，ghost的需要重写
     */
    @Override
    public void run() {                //每次循环先更新状态再行动
        while(true) {
            updateStatus();
            if (monsterStatus != Status.Dead) {
                switch (monsterStatus) {
                    case Chasing: {
                        move(player.worldLoc);
                    }
                    case Attacking: {
                        attack(player);
                    }
                    case OnAttack:{
                        onAttack();
                    }
                    case Waiting:{

                    }
                }
            } else{
                //处理死亡状态
                break;
            }
        }
    }

    /**
     * 检测HP和玩家的距离来更新怪物状态
     */
    public void updateStatus(){

    }

    /**
     * 作出攻击行为
     */
    public void attack(Player player){

    }

    /**
     * 受攻击
     */
    public void onAttack(){

    }

    /**
     * 怪物索敌移动
     */
    public void move(Location playerLocation){
        NextStep nextStep = null;
        do{
            nextStep = findPath(playerLocation);
            Location.moveOneStep(this,nextStep.direction);
            //近战要近身才进入战斗状态，远程考虑在一条直线即可
        }while(nextStep.notMeeting);

    }

    /**
     * 绘图方法，暂时copy类Player的，未完成
     * @param g2d
     */
    public void draw(Graphics2D g2d) {
        BufferedImage img = switch (direction) {
            case U -> (spriteNum == 1) ? up1 : (spriteNum == 2) ? up2 : null;
            case D -> (spriteNum == 1) ? down1 : (spriteNum == 2) ? down2 : null;
            case L -> (spriteNum == 1) ? left1 : (spriteNum == 2) ? left2 : null;
            case R -> (spriteNum == 1) ? right1 : (spriteNum == 2) ? right2 : null;
            default -> null;
        };

        g2d.drawImage(img, worldLoc.getXPosition(), worldLoc.getYPosition(), gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
