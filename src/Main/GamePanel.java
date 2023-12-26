package Main;

import Entity.*;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //Screen Settings
    final int originalTileSize = 16;// 16x16 tiles
    final int scale = 3;// 3x scale, 16x16 tiles * 3 = 48x48 pixels
    public final int tileSize = originalTileSize * scale;// 48x48 pixels
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;// 768 pixels
    final int screenHeight = tileSize * maxScreenRow;// 576 pixels

    final int FPS = 60;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyHandler);

    //Set player's initial position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel(){
        setFocusable(true);
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

//    public void run() {
//        double drawInterval = 1000000000.0 / FPS;//0.016667 seconds
//        double nextDrawTime = System.nanoTime() + drawInterval;
//
//        while(gameThread != null) {
//            //Update: update information of game objects
//            update();
//            //Draw: draw the screen according to the updated information
//            repaint();
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime /= 1000000;//Convert to milliseconds
//
//                if (remainingTime < 0) {
//                    remainingTime = 0;
//                }
//
//                Thread.sleep((long)remainingTime);
//                nextDrawTime += drawInterval;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    /**
     * thread.sleep() is not accurate, so we use the following method instead
     */
    public void run() {
        double drawInterval = 1000000000.0 / FPS;//0.016667 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {//Draw the screen
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        player.draw(g2d);

        g2d.dispose();
    }
}
