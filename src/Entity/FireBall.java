package Entity;

import Main.GamePanel;

public class FireBall extends FlyingObject{
    public FireBall(GamePanel gp) {
        super(gp);

        name = "FireBall";
        speed = 8;
        maxHP = 80;
        HP = maxHP;
        attack = 1;

        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/res/FlyingObject/fireball_up_1.png", gp.tileSize, gp.tileSize);
        up2 = setup("/res/FlyingObject/fireball_up_2.png", gp.tileSize, gp.tileSize);
        down1 = setup("/res/FlyingObject/fireball_down_1.png", gp.tileSize, gp.tileSize);
        down2 = setup("/res/FlyingObject/fireball_down_2.png", gp.tileSize, gp.tileSize);
        right1 = setup("/res/FlyingObject/fireball_right_1.png", gp.tileSize, gp.tileSize);
        right2 = setup("/res/FlyingObject/fireball_right_2.png", gp.tileSize, gp.tileSize);
        left1 = setup("/res/FlyingObject/fireball_left_1.png", gp.tileSize, gp.tileSize);
        left2 = setup("/res/FlyingObject/fireball_left_2.png", gp.tileSize, gp.tileSize);
    }
}
