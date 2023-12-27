package Entity;

public class Location {
    private int xPosition;
    private int yPosition;

    public Location(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * 判断向指定方向是否可以移动
     * @param entity
     * @param direction
     * @return
     */
    public static boolean canMove(Entity entity, Direction direction){
      entity.collisionOn = false;
      Direction tmp = entity.direction;
      entity.direction = direction;
      entity.gamePanel.collisionDetector.checkTile(entity);
      entity.direction = tmp;
      return entity.collisionOn;
    }



    /**
     * 向指定方向移动一步(不作碰撞检测)
     * @param entity
     * @param direction
     */
    public static void moveOneStep(Entity entity, Direction direction) {
            entity.worldLoc.setXPosition(entity.worldLoc.getXPosition() + direction.getDx()*entity.speed);
            entity.worldLoc.setYPosition(entity.worldLoc.getYPosition() + direction.getDy()*entity.speed);
    }
}
