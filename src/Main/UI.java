package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import Object.*;

public class UI {
    GamePanel gp;
    Graphics2D g2d;
    Font arial_40, arial_80B;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public int commandNum = 0;

    public UI(GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Arial",Font.PLAIN,40);
        arial_80B = new Font("Arial",Font.BOLD,80);

        //create HUD object
        SuperObject heart = new OBJ_Heart(gp);
        heart_full = heart.img;
        heart_half = heart.img2;
        heart_blank = heart.img3;
    }

    public void showMessage(String message){
        this.message = message;
        messageOn = true;
        messageCounter = 0;
    }

    public void draw(Graphics2D g2d){
        this.g2d = g2d;

        g2d.setFont(arial_40);
        g2d.setColor(Color.WHITE);

        if (gp.gameState == gp.playState) {
            drawPlayerLife();
        }
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }

    }

    public void drawPauseScreen(){
        g2d.setFont(g2d.getFont().deriveFont(80.0f));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;

        g2d.drawString(text, x, y);
    }

    public int getXForCenteredText(String text){
        int length = (int)g2d.getFontMetrics().getStringBounds(text,g2d).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        // draw maxHP
        while (i < gp.player.maxHP / 2) {
            g2d.drawImage(heart_blank, x, y, null);
            i ++;
            x += gp.tileSize;
        }

        //reset
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        // draw HP
        while (i < gp.player.HP) {
            g2d.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.HP) {
                g2d.drawImage(heart_full, x, y, null);
            }
            i ++;
            x += gp.tileSize;
        }
    };

}
