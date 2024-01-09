package Main;

import Entity.*;
import Entity.Monsters.*;
import Object.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObjects() {
//        gp.objects[gp.currentMap][0] = new OBJ_Heart(gp);

        gp.objects[0][1] = new OBJ_Potion(gp);
        gp.objects[0][1].worldLoc.setXPosition(gp.tileSize * 20);
        gp.objects[0][1].worldLoc.setYPosition(gp.tileSize * 20);

        gp.objects[0][2] = new OBJ_Board(gp);
        gp.objects[0][2].worldLoc.setXPosition(gp.tileSize * 3);
        gp.objects[0][2].worldLoc.setYPosition(gp.tileSize * 3);
    }

    public void setMonsters() {
        gp.monsters[0][0] = new Rajiang(gp);
        gp.monsters[0][0].worldLoc.setXPosition(gp.tileSize * 20);
        gp.monsters[0][0].worldLoc.setYPosition(gp.tileSize * 37);

        gp.monsters[0][1] = new Rajiang(gp);
        gp.monsters[0][1].worldLoc.setXPosition(gp.tileSize * 20);
        gp.monsters[0][1].worldLoc.setYPosition(gp.tileSize * 39);

        gp.monsters[0][2] = new Rajiang(gp);
        gp.monsters[0][2].worldLoc.setXPosition(gp.tileSize * 22);
        gp.monsters[0][2].worldLoc.setYPosition(gp.tileSize * 41);
    }
}
