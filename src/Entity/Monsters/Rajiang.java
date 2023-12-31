package Entity.Monsters;

import Main.GamePanel;
import Entity.*;

import java.util.Random;

public class Rajiang extends Entity{

    public Rajiang(GamePanel gp) {
        super(gp);
        type = 1;
        name = "Rajiang";
        HP = 4;

        hitBox.x = 8;//start from the corner of the image
        hitBox.y = 16;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        hitBox.width = 32;
        hitBox.height = 32;

        direction = Direction.D;
        speed = 1;

        getRajiangImage();
    }

    public void getRajiangImage() {
        up1 = setup("/res/Monster/Monster_U.png", gp.tileSize, gp.tileSize);
        up2 = setup("/res/Monster/Monster_Uw.png", gp.tileSize, gp.tileSize);
        down1 = setup("/res/Monster/Monster_D.png", gp.tileSize, gp.tileSize);
        down2 = setup("/res/Monster/Monster_Dw.png", gp.tileSize, gp.tileSize);
        right1 = setup("/res/Monster/Monster_R.png", gp.tileSize, gp.tileSize);
        right2 = setup("/res/Monster/Monster_Rw.png", gp.tileSize, gp.tileSize);
        left1 = setup("/res/Monster/Monster_L.png", gp.tileSize, gp.tileSize);
        left2 = setup("/res/Monster/Monster_Lw.png", gp.tileSize, gp.tileSize);
    }

    public void setAction() {
        actionLockCounter ++;
        Random random = new Random();

        if (actionLockCounter == 120) {
            actionLockCounter = 0;
            //randomly choose a direction
            int rand = random.nextInt(100) + 1;//1~100
            if (rand <= 25) {
                direction = Direction.L;
            } else if (rand <= 50) {
                direction = Direction.R;
            } else if (rand <= 75) {
                direction = Direction.U;
            } else {
                direction = Direction.D;
            }
        }
    }
}
