package Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum Direction {
    /**
     * 定义了八个方向
     */
    L(-1,0,10), R(1,0,10),U(0,-1,10),D(0,1,10),
    LU(-1,-1,14),LD(-1,1,14),RU(1,-1,14),RD(1,1,14);

    /**
     * 方向的dx，dy属性代表实体沿着该方向移动一格后，坐标的变化值
     * ds则为向该方向移动一格的位移
     */


    private final int dx;
    private final int dy;
    private final int ds;
    Direction(int x,int y,int s)
    {
        this.dx = x;
        this.dy = y;
        this.ds = s;
    }

    /**
     * 建立方向与其属性dx、dy的映射
     */
    private static final Map<Location,Direction> dirMap = new HashMap<>();
    static {
        for(Direction dir : Direction.values()){
            dirMap.put(new Location(dir.getDx(), dir.getDy()),dir);
        }
    }

    /**
     * 该方法可获取邻接tile相对当前tile的方向
     * @param dx 邻接tile的x坐标-当前tile的x坐标
     * @param dy 邻接tile的y坐标-当前tile的y坐标
     * @return 相对方向
     */
    public static Direction getDirection(int dx,int dy){
        return dirMap.get(new Location(dx,dy));
    }

    private static final Random rd = new Random(System.currentTimeMillis());

    /**
     * 获取随机方向
     * @return 以时间为种子的随机方向
     */
    public static Direction getRandomDirection(){
        return Direction.values()[rd.nextInt(Direction.values().length)];
    }

    /**
     * 获取相反方向
     * @param dir 指定方向
     * @return 相反方向
     */
    public static Direction getNegativeDirection(Direction dir){
        return getDirection(-dir.dx,-dir.dy);
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
