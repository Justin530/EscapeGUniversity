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
    public String currentDialogue = "";
    int charIndex = 0;
    String combinedText = "";

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
        if (!messageI.added) {
            message.add(messageI);
            messageCounter.add(0);
            messageI.added = true;
        }
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
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
        }
        if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }
        if (gp.gameState == gp.storyState) {
            drawStoryScreen();
        }
    }

    public void drawPauseScreen(){
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x, y;
        String text;
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 110f));

        text = "PAUSED";
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

        //Continue
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 40f));
        text = "continue";
        x = getXForCenteredText(text);
        y += gp.tileSize * 4;
        g2d.drawString(text, x, y);
        if (commandNum == 0) {
            g2d.drawString(">", x - 40, y);
        }

        //Restart
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 40f));
        text = "restart";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2d.drawString(text, x, y);
        if (commandNum == 1) {
            g2d.drawString(">", x - 40, y);
        }

        //Quit
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 40f));
        text = "quit";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2d.drawString(text, x, y);
        if (commandNum == 2) {
            g2d.drawString(">", x - 40, y);
        }
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

    public void drawDialogueScreen() {
        //the window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - gp.tileSize * 4;
        int height = gp.tileSize * 4;

        drawSubwindow(x, y, width, height);

        Font chineseFont = new Font("宋体", Font.PLAIN, 20); // 使用宋体字体，12号字体大小
        g2d.setFont(chineseFont); // 设置Graphics2D的字体为宋体
        x += gp.tileSize;
        y += gp.tileSize;

        char characters[] = currentDialogue.toCharArray();

        if (charIndex < characters.length) {
            combinedText += characters[charIndex];
            charIndex ++;
        }
        if (gp.keyHandler.interactPressed){
            charIndex = 0;
            combinedText = "";

//            System.out.println(gp.objects[gp.currentMap][gp.player.objectIndex].dialogueIndex);
            gp.objects[gp.currentMap][gp.player.objectIndex].dialogueIndex ++;
//            System.out.println(gp.objects[gp.currentMap][gp.player.objectIndex].dialogues[gp.objects[gp.currentMap][gp.player.objectIndex].dialogueIndex]);
            gp.keyHandler.interactPressed = false;
        }

        for (String line : combinedText.split("\n")) {
            g2d.drawString(line, x, y);
            y += g2d.getFontMetrics().getHeight();
        }
    }

    public void drawSubwindow(int x, int y, int width, int height) {
        Color color = new Color(0, 0, 0, 200);
        g2d.setColor(color);
        g2d.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255, 255, 255);
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 35, 35);
    }

    public void drawStoryScreen(){
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        Font chineseFont = new Font("宋体", Font.PLAIN, 20); // 使用宋体字体，12号字体大小
        g2d.setFont(chineseFont); // 设置Graphics2D的字体为宋体

        g2d.setColor(Color.WHITE);
        g2d.drawString("按 F 键进行交互", gp.screenWidth - gp.tileSize * 5, gp.screenHeight - gp.tileSize);

        int x = gp.tileSize * 3;
        int y = gp.tileSize / 2 + gp.tileSize;

        char characters[] = currentDialogue.toCharArray();

        if (charIndex < characters.length) {
            combinedText += characters[charIndex];
            charIndex ++;
        }
        if (gp.keyHandler.interactPressed){
            charIndex = 0;
            combinedText = "";

            if (gp.gameState == gp.storyState) {
                gp.story.dialogueIndex ++;
                gp.keyHandler.interactPressed = false;
            }
        }

        for (String line : combinedText.split("\n")) {
            g2d.drawString(line, x, y);
            y += g2d.getFontMetrics().getHeight();
        }
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
        if (commandNum == 0) {
            g2d.drawString(">", x - 40, y);
        }

        //Quit
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 40f));
        text = "quit";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2d.drawString(text, x, y);
        if (commandNum == 1) {
            g2d.drawString(">", x - 40, y);
        }
    }

    public void drawCharacterScreen() {

    }
}
