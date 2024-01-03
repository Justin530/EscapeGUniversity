package Entity.Monsters;

import Entity.*;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;



/*
暂时考虑每个怪物单独对应一个线程，线程负责更新怪物状态并作出行为

一些方法只做了近战的（zombie的），ghost需要重写

需要的信息由gamePanel提供

ToDo：
   攻击，绘图相关的代码待完成
 */
public class Monster extends Entity {
    private int attack;
    private int chaseRadius;
    private int attackRadius;
    private int attackInterval;
    public static Random possibilityController = new Random(System.currentTimeMillis());
  public enum Status {
        Chasing, Wandering,Attacking,OnAttack,Dead
    }
    public Status monsterStatus = Status.Chasing;
    Player player;
  public Monster(GamePanel gp) {
      super(gp);
      type = 1;
      name = "Rajiang";
      maxHP = 8;
      HP = maxHP;

      player = gp.player;

      hitBox.x = 8;//start from the corner of the image
      hitBox.y = 16;
      hitBoxDefaultX = hitBox.x;
      hitBoxDefaultY = hitBox.y;
      hitBox.width = 32;
      hitBox.height = 32;

      direction = Direction.D;
      speed = 1;

      getMonsterImage();
  }

    /**
     * 此类包装有寻路算法中下一步的信息
     */
    public class NextStep {
        Direction direction;   //当前节点的方向（相对怪物）
        boolean notMeeting;//当notMeeting == False 时标志找到玩家
        Direction prev;

        NextStep(Direction dir, boolean notMeeting, Direction prev) {
            this.direction = dir;
            this.notMeeting = notMeeting;
            this.prev = prev;
        }

        static int calculateManhattan(Location currentLoc, Location destination) {
            return Math.abs(currentLoc.getXPosition() - destination.getXPosition()) + Math.abs(currentLoc.getYPosition() - destination.getYPosition());
        }
    }


    public void setAction() {
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
                        onAttack();//onAttack未实现
                    }
                    case Wandering: {
                        if(possibilityController.nextInt(2)==1) //有1/2可能随机移动
                            patrol();
                    }
                }
            } else {
                // TODO : 处理死亡状态
            }
    }

    /**
     * 检测HP和与玩家的曼哈顿距离来更新怪物状态
     */
    public void updateStatus() {
        if (this.getHP() == 0)
            this.monsterStatus = Status.Dead;
        else {
            int manhattan = Math.abs(this.worldLoc.getXPosition() - this.player.worldLoc.getXPosition()) + Math.abs(this.worldLoc.getYPosition() - this.player.worldLoc.getYPosition());
            if (manhattan <= this.getAttackRadius())
                this.monsterStatus = Status.Attacking;
            else if (manhattan > attackRadius && manhattan <= chaseRadius)
                this.monsterStatus = Status.Chasing;
            else if (manhattan > chaseRadius)
                this.monsterStatus = Status.Wandering;
            //OnAttack功能尚未实现
        }
    }

    /**
     * @param dest 目标位置
     * @return 下一步的方向以及下一步和玩家是否相遇（包装在NextStep类对象里）
     */
    public NextStep findPath(Location dest, Direction prev) {
        if (NextStep.calculateManhattan(this.worldLoc, dest) == 1) {
            //曼哈顿距离为1，和玩家相遇
            Direction nextDir = Direction.getDirection(dest.getXPosition() - this.worldLoc.getXPosition(), dest.getYPosition() - this.worldLoc.getYPosition());
            return new NextStep(nextDir, false, Direction.getNegativeDirection(nextDir));
        } else {
            int F, G, H;
            int Min_F = Integer.MAX_VALUE; //将当前最小F设为无穷大（Integer最大值），方便比较
            Direction bestDirection = null;//最优行动方向
            List<Direction> validDirections = new ArrayList<>();
            //筛选可移动的有效方向
            for (Direction dir : Direction.values()) {
                if (canMove(this, dir))
                    validDirections.add(dir);
            }
            validDirections.remove(prev);  //防止僵尸卡墙
            if (validDirections.isEmpty()) {
                    bestDirection = prev ;
            } else {
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
            }
                return new NextStep(bestDirection, true, Direction.getNegativeDirection(bestDirection));
        }
    }

    /**
     * 怪物索敌移动
     */
    public void move(Location playerLocation) {
        NextStep nextStep;
        do {
            nextStep = findPath(playerLocation, Direction.getNegativeDirection(this.direction));
            Location.moveOneStep(this, nextStep.direction);
        } while (nextStep.notMeeting);
        //未和玩家相遇就一直执行寻路方法
    }

    /**
     * 待机状态，无目的漫游
     */
    public void patrol() {
        Direction randomDirection = Direction.getRandomDirection();
        if (canMove(this, randomDirection))
            Location.moveOneStep(this, randomDirection);
    }

    public int getHP() {
        return HP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getAttack() {
        return attack;
    }

    public Status getMonsterStatus() {
        return monsterStatus;
    }
    public int getChaseRadius(){
        return chaseRadius;
    }
    public int getAttackRadius(){
        return attackRadius;
    }
    public int getAttackInterval(){
        return attackInterval;
    }
    public Player getPlayer() {
        return player;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }


    public void setMonsterStatus(Status monsterStatus) {
        this.monsterStatus = monsterStatus;
    }
    /**
     * 判断向指定方向是否可以移动
     * @param entity 待移动的实体
     * @param direction 移动方向
     * @return 是否可向指定方向移动（true/false）
     */
    public static boolean canMove(Entity entity, Direction direction){
        entity.collisionOn = false;
        Direction tmp = entity.direction;
        entity.direction = direction;
        entity.gp.collisionDetector.checkTile(entity);
        entity.gp.collisionDetector.checkPlayer(entity);
        entity.direction = tmp;
        return entity.collisionOn;
    }

    /*-------------------------以下为暂时无法完成的方法----------------------------------*/

    /**
     * TODO：可能需要添加击退效果
     */
    public void attack(Player player) {
        player.HP = player.HP-this.attack;
        try {
            Thread.sleep(this.getAttackInterval());//攻击后摇
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * TODO：实现受到攻击的反应
     */
    public void onAttack() {

    }

    public void getMonsterImage() {
        up1 = setup("/res/Monster/Monster_U.png", gp.tileSize, gp.tileSize);
        up2 = setup("/res/Monster/Monster_Uw.png", gp.tileSize, gp.tileSize);
        down1 = setup("/res/Monster/Monster_D.png", gp.tileSize, gp.tileSize);
        down2 = setup("/res/Monster/Monster_Dw.png", gp.tileSize, gp.tileSize);
        right1 = setup("/res/Monster/Monster_R.png", gp.tileSize, gp.tileSize);
        right2 = setup("/res/Monster/Monster_Rw.png", gp.tileSize, gp.tileSize);
        left1 = setup("/res/Monster/Monster_L.png", gp.tileSize, gp.tileSize);
        left2 = setup("/res/Monster/Monster_Lw.png", gp.tileSize, gp.tileSize);
    }
}
