package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;


/*
暂时考虑每个怪物单独对应一个线程，线程负责更新怪物状态并作出行为

一些方法只做了近战的（zombie的），ghost需要重写

需要的信息由gamePanel提供

 */
public class Monster extends Entity implements Runnable {
    private int HP;
    private int attack;

    private enum Status {
        Chasing, Waiting, Attacking, OnAttack, Dead
    }

    private Status monsterStatus = Status.Chasing;
    Player player = gamePanel.player;

    Monster() {
    }

    public Monster(int HP, int attack) {
        this.HP = HP;
        this.attack = attack;
        this.monsterStatus = Status.Waiting;

    }

    /**
     * 此类储存寻路中下一步的信息
     */
    public class NextStep {
        Direction direction;   //当前节点的方向（相对怪物）
        boolean notMeeting;//当notMeeting == False 时标志找到玩家

        NextStep(Direction dir, boolean notMeeting) {
            this.direction = dir;
            this.notMeeting = notMeeting;
        }

        static int calculateManhattan(Location currentLoc, Location destination) {
            return Math.abs(currentLoc.getXPosition() - destination.getXPosition()) + Math.abs(currentLoc.getYPosition() - destination.getYPosition());
        }
    }

    /**
     * 根据怪物状态控制其行为的线程run()方法，但目前只实现了zombie的，ghost的需要重写
     */
    @Override
    public void run() {                //每次循环先更新状态再行动
        while (true) {
            updateStatus();
            if (monsterStatus != Status.Dead) {
                switch (monsterStatus) {
                    case Chasing: {
                        move(player.worldLoc);
                    }
                    case Attacking: {
                        attack(player);
                    }
                    case OnAttack: {
                        onAttack();//未实现
                    }
                    case Waiting: {
                        patrol();
                    }
                }
            } else {
                //处理死亡状态
                break;
            }
        }
    }

    /**
     * 检测HP和与玩家的曼哈顿距离来更新怪物状态
     */
    public void updateStatus() {
        if (this.HP == 0)
            this.monsterStatus = Status.Dead;
        else {
            int manhattan = Math.abs(this.worldLoc.getXPosition() - this.player.worldLoc.getXPosition()) + Math.abs(this.worldLoc.getYPosition() - this.player.worldLoc.getYPosition());
            if (manhattan == 1)
                this.monsterStatus = Status.Attacking;
            else if (manhattan > 1 && manhattan <= 10)
                this.monsterStatus = Status.Chasing;
            else if (manhattan > 10)
                this.monsterStatus = Status.Waiting;
            //OnAttack功能尚未实现
        }
    }

    /**
     * @param dest 目标位置
     * @return 下一步的方向以及下一步和玩家是否相遇
     */
    public NextStep findPath(Location dest) {
        if (NextStep.calculateManhattan(this.worldLoc, dest) == 1) {
            //曼哈顿距离为1，和玩家相遇
            Direction nextDir = Direction.getDirection(dest.getXPosition() - this.worldLoc.getXPosition(), dest.getYPosition() - this.worldLoc.getYPosition());
            return new NextStep(nextDir, false);
        } else {
            int F, G, H;
            int Min_F = Integer.MAX_VALUE; //将当前最小F设为无穷大（Integer最大值），方便比较
            Direction bestDirection = null;//最优行动方向
            List<Direction> validDirections = new ArrayList<>();
            //筛选可移动的有效方向
            for (Direction dir : Direction.values()) {
                if (Location.canMove(this, dir))
                    validDirections.add(dir);
            }
            for (Direction validDirection : validDirections) {
                G = validDirection.getDs();
                H = NextStep.calculateManhattan(new Location(worldLoc.getXPosition() + validDirection.getDy(), worldLoc.getYPosition() + validDirection.getDy()), dest);
                F = G + H;
                //计算F，取F最小的方向为最优
                if (F < Min_F) {
                    Min_F = F;
                    bestDirection = validDirection;
                }
            }
            return new NextStep(bestDirection, true);
        }
    }

    /**
     * 怪物索敌移动
     */
    public void move(Location playerLocation) {
        NextStep nextStep;
        do {
            nextStep = findPath(playerLocation);
            Location.moveOneStep(this, nextStep.direction);
        } while (nextStep.notMeeting);
        //未和玩家相遇就一直执行寻路方法
    }

    /**
     * 待机状态，无目的漫游
     */
    public void patrol() {
        Direction randomDirection = Direction.getRandomDirection();
        if (Location.canMove(this, randomDirection))
            Location.moveOneStep(this, randomDirection);
    }
    /**********************以下为暂时无法不全的方法*****************************************/
    /**
     * 作出攻击行为
     */
    public void attack(Player player) {
        //player.HP -= this.attack;
    }

    /**
     * 受攻击
     */
    public void onAttack() {

    }


    /**
     * 绘图方法，暂时copy类Player的，未完成
     *
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
