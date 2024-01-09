package Entity.FlyingObject;

import Main.GamePanel;

public class Bullet extends FlyingObject {
    public Bullet(GamePanel gp) {
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
        up1 = setup("/res/FlyingObject/bullet_up.png", gp.tileSize, gp.tileSize);
        up2 = setup("/res/FlyingObject/bullet_up_left.png", gp.tileSize, gp.tileSize);
        down1 = setup("/res/FlyingObject/bullet_down.png", gp.tileSize, gp.tileSize);
        down2 = setup("/res/FlyingObject/bullet_down_right.png", gp.tileSize, gp.tileSize);
        right1 = setup("/res/FlyingObject/bullet_right.png", gp.tileSize, gp.tileSize);
        right2 = setup("/res/FlyingObject/bullet_up_right.png", gp.tileSize, gp.tileSize);
        left1 = setup("/res/FlyingObject/bullet_left.png", gp.tileSize, gp.tileSize);
        left2 = setup("/res/FlyingObject/bullet_down_left.png", gp.tileSize, gp.tileSize);
    }
}
