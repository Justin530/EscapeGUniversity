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

        gp.objects[0][4] = new OBJ_Trapdoor(gp);

        gp.objects[0][5] = new OBJ_Key(gp);
        gp.objects[0][5].worldLoc.setXPosition(gp.tileSize * 2);
        gp.objects[0][5].worldLoc.setYPosition(gp.tileSize * 2);
    }

    public void setMonsters() {
        gp.monsters[0][0] = new Yukari(gp);
        gp.monsters[0][0].worldLoc.setXPosition(gp.tileSize * 24);
        gp.monsters[0][0].worldLoc.setYPosition(gp.tileSize * 33);

        gp.monsters[0][1] = new Rajiang(gp);
        gp.monsters[0][1].worldLoc.setXPosition(gp.tileSize * 20);
        gp.monsters[0][1].worldLoc.setYPosition(gp.tileSize * 39);

        gp.monsters[0][2] = new Rajiang(gp);
        gp.monsters[0][2].worldLoc.setXPosition(gp.tileSize * 22);
        gp.monsters[0][2].worldLoc.setYPosition(gp.tileSize * 41);

        gp.monsters[0][3] = new Rajiang(gp);
        gp.monsters[0][3].worldLoc.setXPosition(gp.tileSize * 25);
        gp.monsters[0][3].worldLoc.setYPosition(gp.tileSize * 11);

        gp.monsters[0][4] = new Rajiang(gp);
        gp.monsters[0][4].worldLoc.setXPosition(gp.tileSize * 46);
        gp.monsters[0][4].worldLoc.setYPosition(gp.tileSize * 12);

        gp.monsters[0][5] = new Rajiang(gp);
        gp.monsters[0][5].worldLoc.setXPosition(gp.tileSize * 43);
        gp.monsters[0][5].worldLoc.setYPosition(gp.tileSize * 28);

        gp.monsters[0][6] = new Rajiang(gp);
        gp.monsters[0][6].worldLoc.setXPosition(gp.tileSize * 23);
        gp.monsters[0][6].worldLoc.setYPosition(gp.tileSize * 47);

        gp.monsters[0][7] = new Rajiang(gp);
        gp.monsters[0][7].worldLoc.setXPosition(gp.tileSize * 40);
        gp.monsters[0][7].worldLoc.setYPosition(gp.tileSize * 46);

        gp.monsters[0][8] = new Rajiang(gp);
        gp.monsters[0][8].worldLoc.setXPosition(gp.tileSize * 22);
        gp.monsters[0][8].worldLoc.setYPosition(gp.tileSize * 41);

    }
}
