package Entity;

public enum Direction {
    /**
     * 定义了八个方向
     */
    L(1,0), R(-1,0),U(0,1),D(0,-1),
    LU(1,1),LD(1,-1),RU(-1,1),RD(-1,-1);

    /**
     * 方向的dx，dy属性代表实体沿着该方向移动一格后，坐标的变化值
     */

        private int dx;
        private int dy;
        Direction(int x,int y)
        {
            this.dx = x;
            this.dy = y;
        }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
