package Entity;
/*
暂时考虑每个怪物单独对应一个线程，线程负责更新怪物状态并作出行为
一些方法只做了近战的（zombie的），需要完善
需要地图信息和玩家坐标的方法目前需要等待组长提供相关接口
 */
public class Monster extends Entity implements Runnable {
    private int HP;
    private enum Status {
        Chasing,Waiting,OnAttack,Dead;
    }
    public Status monsterStatus = Status.Chasing;
    Monster(){
    }

    public Monster( int HP) {
        this.HP = HP;
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
        NextStep(Location currentLoc,Location destination,Direction dir,boolean canMove){
            this.destination = destination;
            this.currentLoc = currentLoc;
            this.direction = dir;
            this.notMeeting = canMove;
            //G = 10 或 14 使用勾股定理计算
            G =  (int) (10*Math.sqrt(direction.getDx()*direction.getDx()+direction.getDy()*direction.getDy()));
            //计算曼哈顿距离
            H = Math.abs(currentLoc.getXPosition()-destination.getXPosition())+Math.abs(currentLoc.getYPosition()-destination.getYPosition());
            F = G+H;
        }
    }

    /**
     * A*算法寻路，G=10、14，H=曼哈顿距离
     * @return
     */
    public NextStep findPath(Location playerlocation){

        return null;
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
                        move(null);
                    }
                    case Waiting: {
                        attack();
                    }
                    case OnAttack:{
                        onAttack();
                    }
                }
            } else{
                //处理死亡状态
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
    public void attack(){

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
        while(monsterStatus == Status.Chasing){
            NextStep nextStep = findPath(playerLocation);
            if(nextStep.notMeeting)
                Location.moveOneStep(this,nextStep.direction);
            else
                monsterStatus = Status.Waiting; //切换索敌状态
            //近战要近身才进入战斗状态，远程考虑在一条直线即可
        }
    }

    /**
     * researchPlayer
     * 接受坐标
     * 等待接口
     */
    public Location researchPlayer(){
        return null;
    }

    /**
     * detectCollision
     * 碰撞检测 对接地图
     * ps:移动的碰撞检测计划封装在Location中
     */

}
