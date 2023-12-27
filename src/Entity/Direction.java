package Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum Direction {
    /**
     * 定义了八个方向
     */
    L(-1,0,10), R(1,0,10),U(0,1,10),D(0,-1,10),
    LU(-1,1,14),LD(-1,-1,14),RU(1,1,14),RD(1,-1,14);

    /**
     * 方向的dx，dy属性代表实体沿着该方向移动一格后，坐标的变化值
     */

        private int dx;
        private int dy;
        private int ds;
        Direction(int x,int y,int s)
        {
            this.dx = x;
            this.dy = y;
            this.ds = s;
        }
        private static Map<Location,Direction> dirMap = new HashMap<>();
        static {
            for(Direction dir : Direction.values()){
                dirMap.put(new Location(dir.getDx(), dir.getDy()),dir);
            }
        }
        public static Direction getDirection(int dx,int dy){
            return dirMap.get(new Location(dx,dy));
        }
    private static Random rd = new Random(System.currentTimeMillis());
        public static Direction getRandomDirection(){
            return Direction.values()[rd.nextInt(Direction.values().length)];
        }
    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getDs() {
        return ds;
    }
}
