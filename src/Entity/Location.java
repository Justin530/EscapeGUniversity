package Entity;

import Main.GamePanel;

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
      entity.gamePanel.collisionDetector.checkTile(entity);
      return entity.collisionOn;
    }



    /**
     * 向指定方向移动一步(不作碰撞检测)
     * @param entity
     * @param direction
     */
    public static void moveOneStep(Entity entity, Direction direction) {
            entity.loc.setXPosition(entity.loc.getXPosition() + direction.getDx()*entity.speed);
            entity.loc.setYPosition(entity.loc.getYPosition() + direction.getDy()*entity.speed);
    }
}
