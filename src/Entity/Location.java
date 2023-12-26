package Entity;

public class Location {
    private int xPosition;
    private int yPosition;

    public Location(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * 判断向指定方向是否可以移动
     * @param location
     * @param direction
     * @return
     */
    public static boolean canMove(Location location, Direction direction){
        return true;
    }

    /**
     * 向指定方向移动一步
     * @param location
     * @param direction
     */
    public static void moveOneStep(Location location, Direction direction) {
        if(canMove(location,direction)) {
            location.xPosition += direction.getDx();
            location.yPosition += direction.getDy();
        }
    }
}
