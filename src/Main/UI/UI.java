package Main.UI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Main.GamePanel;
import Object.*;

public class UI {
    GamePanel gp;
    Graphics2D g2d;
    Font arial_40, arial_80B;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    ArrayList<Message> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
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

    public void addMessage(String text) {
        Message messageI = new Message(gp, text);
        message.add(messageI);
        messageCounter.add(0);
    }

    public void addMessage(Message message) {
        this.message.add(message);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2d){
        this.g2d = g2d;

        g2d.setFont(arial_40);
        g2d.setColor(Color.WHITE);

        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawSystemMessage();
            drawEntityMessage();
        }
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
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

    public void drawSystemMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 30F));

        for (int i = 0; i < message.size(); i ++) {
            Message messageI = this.message.get(i);
            if (messageI != null) {
                g2d.setColor(Color.WHITE);
                g2d.drawString(messageI.message, messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += gp.tileSize;

                if (messageCounter.get(i) > 120) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawEntityMessage() {
        //todo
    }

    public void drawGameOverScreen(){
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x, y;
        String text;
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 110f));

        text = "GAME OVER";
        //Shadow
        g2d.setColor(Color.BLACK);
        x = getXForCenteredText(text);
        y = gp.tileSize * 4;
        g2d.drawString(text, x, y);
        //Main
        g2d.setColor(Color.WHITE);
        x = getXForCenteredText(text) - 5;
        y = gp.tileSize * 4 - 5;
        g2d.drawString(text, x, y);

        //Restart
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 40f));
        text = "restart";
        x = getXForCenteredText(text);
        y += gp.tileSize * 4;
        g2d.drawString(text, x, y);
        if (commandNum == 1) {
            g2d.drawString(">", x - 40, y);
        }
    }
}
