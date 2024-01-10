package AI;

import Entity.Direction;
import Entity.Location;
//精简版的寻路算法使用的Node
public class QuickNode {
    public Direction direction;   //当前节点的方向（相对怪物）

    QuickNode(Direction dir) {
        this.direction = dir;
    }


}