package Main.UI;

import Main.GamePanel;

public class Message {
    GamePanel gp;
    public String message;
    public int messageX;//the x position of the message
    public int messageY;//the y position of the message
    public boolean added = false;

    public Message(GamePanel gp) {
        this.gp = gp;
        messageX = gp.tileSize;
        messageY = gp.tileSize * 4;
    }

    public Message(GamePanel gp, String message) {
        this.gp = gp;
        this.message = message;
        messageX = gp.tileSize;
        messageY = gp.tileSize * 4;
    }
}
