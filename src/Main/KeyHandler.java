package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler  implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed,
            attackPressed, changeWeaponPressed, greatPressed, interactPressed;



    GamePanel gp;
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.playState) {
            playState(code);
        }
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }
        else if (gp.gameState == gp.characterState) {
            if (code == KeyEvent.VK_B) {
                gp.gameState = gp.playState;
            }
        }
        else if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_F) {
                interactPressed = true;
            }
        }
        else if (gp.gameState == gp.storyState) {
            if (code == KeyEvent.VK_F) {
                interactPressed = true;
            }
        }
        else if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }
        else if (gp.gameState == gp.endingState) {
            if (code == KeyEvent.VK_F) {
                interactPressed = true;
            }
        }
        else if (gp.gameState == gp.victoryState) {
            victoryState(code);
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
            changeWeaponPressed = false;
        }
        if (code == KeyEvent.VK_L){
            greatPressed = false;
        }
        if (code == KeyEvent.VK_F){
            interactPressed = false;
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
            changeWeaponPressed = true;
        }
        if (code == KeyEvent.VK_L){
            greatPressed = true;
        }
        if (code == KeyEvent.VK_F){
            interactPressed = true;
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

    public void pauseState(int code) {
        if (code == KeyEvent.VK_W){
            gp.ui.commandNum --;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        }
        if (code == KeyEvent.VK_S){
            gp.ui.commandNum ++;
            if (gp.ui.commandNum >2) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_F) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
            }
            else if (gp.ui.commandNum == 1) {
                gp.restart();
            }
            else if (gp.ui.commandNum == 2) {
                System.exit(0);
            }
        }
    }

    public void gameOverState(int code){
        if (code == KeyEvent.VK_W){
            gp.ui.commandNum --;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
        }
        if (code == KeyEvent.VK_S){
            gp.ui.commandNum ++;
            if (gp.ui.commandNum >1) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_F) {
            if (gp.ui.commandNum == 0) {
                gp.restart();
            }
            else if (gp.ui.commandNum == 1) {
                System.exit(0);
            }
        }
    }

    public void victoryState(int code){
        if (code == KeyEvent.VK_W){
            gp.ui.commandNum --;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
        }
        if (code == KeyEvent.VK_S){
            gp.ui.commandNum ++;
            if (gp.ui.commandNum >1) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_F) {
            if (gp.ui.commandNum == 0) {
                gp.restart();
            }
            else if (gp.ui.commandNum == 1) {
                System.exit(0);
            }
        }
    }
}
