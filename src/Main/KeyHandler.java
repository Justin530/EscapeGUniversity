package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler  implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed,
            attackPressed, shotKeyPressed, greatPressed;



    GamePanel gp;
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.playState) {
            playState(code);
        }
        else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }
        else if (gp.gameState == gp.characterState) {
            if (code == KeyEvent.VK_B) {
                gp.gameState = gp.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W){
            upPressed = false;
        }
        if (code == KeyEvent.VK_S){
            downPressed = false;
        }
        if (code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if (code == KeyEvent.VK_J){
            attackPressed = false;
        }
        if (code == KeyEvent.VK_K){
            shotKeyPressed = false;
        }
        if (code == KeyEvent.VK_L){
            greatPressed = false;
        }
    }

    public void playState(int code){
        if (code == KeyEvent.VK_W){
            upPressed = true;
        }
        if (code == KeyEvent.VK_S){
            downPressed = true;
        }
        if (code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if (code == KeyEvent.VK_J){
            attackPressed = true;
        }
        if (code == KeyEvent.VK_K){
            shotKeyPressed = true;
        }
        if (code == KeyEvent.VK_L){
            greatPressed = true;
        }
        if (code == KeyEvent.VK_P){
            if(gp.gameState == gp.playState){
                gp.gameState = gp.pauseState;
            }
        }
        if (code == KeyEvent.VK_B){
            if(gp.gameState == gp.playState){
                gp.gameState = gp.characterState;
            }
        }
    }
}
