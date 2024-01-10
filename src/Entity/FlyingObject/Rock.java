package Entity.FlyingObject;

import Entity.FlyingObject.FlyingObject;
import Main.GamePanel;

public class Rock extends FlyingObject {
    public Rock(GamePanel gp) {
        super(gp);

        name = "Rock";
        speed = 8;
        maxHP = 80;
        HP = maxHP;
        attack = 1;

        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/res/FlyingObject/rock_down_1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/res/FlyingObject/rock_down_1.png", gp.tileSize, gp.tileSize);
        down1 = setup("/res/FlyingObject/rock_down_1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/res/FlyingObject/rock_down_1.png", gp.tileSize, gp.tileSize);
        right1 = setup("/res/FlyingObject/rock_down_1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/res/FlyingObject/rock_down_1.png", gp.tileSize, gp.tileSize);
        left1 = setup("/res/FlyingObject/rock_down_1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/res/FlyingObject/rock_down_1.png", gp.tileSize, gp.tileSize);
    }
}