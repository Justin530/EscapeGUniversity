package AI;

import Entity.Direction;
import Entity.Entity;
import Entity.Location;

import java.util.ArrayList;
import java.util.List;
import java.math.*;

import static Entity.Direction.*;

//四方向的精简版寻路算法，消耗资源少，速度快，但可能卡墙
public class QuickPathFinder {
    public static Direction[] fourDir = {L,U,R,D};
    static int calculateManhattan(Location currentLoc, Location destination) {
        return Math.abs(currentLoc.getXPosition() - destination.getXPosition()) + Math.abs(currentLoc.getYPosition() - destination.getYPosition());
    }
    public static boolean canMove(Entity entity, Direction direction){
        entity.collisionOn = false;
        Direction tmp = entity.direction;
        entity.direction = direction;
        entity.gp.collisionDetector.checkTile(entity);
        entity.gp.collisionDetector.checkPlayer(entity);
        entity.direction = tmp;
        return !entity.collisionOn;
    }
    public static Direction findNextStep(Entity src, Location dest) {
        if (calculateManhattan(src.worldLoc, dest) == 48) {
            //曼哈顿距离为1，和玩家相遇
            Direction nextDir = Direction.getDirection((int) Math.signum(dest.getXPosition() - src.worldLoc.getXPosition()), (int) Math.signum(dest.getYPosition() - src.worldLoc.getYPosition()));
            return nextDir;
        } else {
            int F, G, H;
            int Min_F = Integer.MAX_VALUE; //将当前最小F设为无穷大（Integer最大值），方便比较
            Direction bestDirection = D ;//最优行动方向
            List<Direction> validDirections = new ArrayList<>();
            //筛选可移动的有效方向
            for (Direction dir : fourDir) {
                if (canMove(src, dir))
                    validDirections.add(dir);
            }
            for (Direction validDirection : validDirections) {
                    G = validDirection.getDs();
                    H = calculateManhattan(new Location(src.worldLoc.getXPosition() + validDirection.getDx(), src.worldLoc.getYPosition() + validDirection.getDy()), dest);
                    F = G + H;
                    //计算F，取F最小的方向为最优
                    if (F < Min_F) {
                        Min_F = F;
                        bestDirection = validDirection;
                    }
                }
            return bestDirection;
        }
    }
}
