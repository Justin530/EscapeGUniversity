package Entity;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

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
     * 寻找路径的方法，使用A*算法。
     *
     * @param playerLocation 玩家的位置
     * @return 下一步的信息，包括当前位置、目标位置、方向和是否找到玩家
     */
    public NextStep findPath(Location playerLocation) {
        // 当前怪物的位置
        Location current = this.loc;

        // 检查当前怪物与目标的曼哈顿距离是否为1，如果是，直接返回当前位置作为终点
        if (Math.abs(current.getXPosition() - playerLocation.getXPosition()) +
                Math.abs(current.getYPosition() - playerLocation.getYPosition()) == 1) {
            /**
             * 这里未处理！！！！！如果当前坐标与目标的曼哈顿距离为 1 的时候怎么处理？？？
             */
            return null;
        }

        // 初始化最小F值的方向和F值
        Direction minFDirection = null;
        int minFValue = Integer.MAX_VALUE;//这里先直接设定成一个充分大的值

        // 获取当前位置上可以移动的方向列表
        List<Direction> validDirections = this.getValidDirections();

        // 遍历所有可行方向
        for (Direction direction : validDirections) {
            // 创建合法的邻居位置
            Location neighbor = new Location(current.getXPosition(), current.getYPosition());

            // 计算移动的距离（这里的dx和dy始终为0，因为仅计算方向）
            int dx = neighbor.getXPosition() - current.getXPosition();
            int dy = neighbor.getYPosition() - current.getYPosition();

            // 计算G值，使用勾股定理计算距离
            int tentativeGScore = (int) (10 * Math.sqrt(dx * dx + dy * dy));

            // 计算H值，曼哈顿距离
            int H = Math.abs(neighbor.getXPosition() - playerLocation.getXPosition())
                    + Math.abs(neighbor.getYPosition() - playerLocation.getYPosition());

            // 计算F值
            int F = tentativeGScore + H;

            // 更新最小F值的方向和F值
            if (F < minFValue) {
                minFValue = F;
                //就是我们最终选定的Nextstep的方向
                minFDirection = direction;
            }
        }

        // 如果找到了最小F值的方向，则返回下一步的信息
        if (minFDirection != null) {
            return new NextStep(current, this.loc, minFDirection, true);
        }

        // 如果未找到路径，返回null或者采取其他处理方式(这里可能)
        return null;
    }

    /**
     * 获取当前位置上可以移动的方向列表。
     * @return 可行的方向列表
     */
    private List<Direction> getValidDirections() {
        // 初始化可行方向列表
        List<Direction> validDirections = new ArrayList<>();

        // 遍历所有方向
        for (Direction direction : Direction.values()) {
            // 判断是否可以移动到该方向
            if (Location.canMove(this, direction)) {
                validDirections.add(direction);
            }
        }

        return validDirections;
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
                        move(player.loc);
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

        g2d.drawImage(img, loc.getXPosition(), loc.getYPosition(), gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
