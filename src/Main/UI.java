package Main;

import java.awt.*;

public class UI {
    GamePanel gp;
    Graphics2D g2d;
    Font arial_40, arial_80B;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    public UI(GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Arial",Font.PLAIN,40);
        arial_80B = new Font("Arial",Font.BOLD,80);
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
            //todo
        }
        if (gp.gameState == gp.pauseState) {
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
}
