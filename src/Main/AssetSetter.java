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
        gp.objects[0] = new OBJ_Heart(gp);
    }

    public void setMonsters() {
        gp.monsters[0] = new Rajiang(gp);
        gp.monsters[0].worldLoc.setXPosition(gp.tileSize * 23);
        gp.monsters[0].worldLoc.setYPosition(gp.tileSize * 36);
    }
}
