package Main;

import Entity.Monster;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setMonsters() {
        gp.monsters[0] = new Monster();
    }
}
