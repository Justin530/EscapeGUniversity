package Main;

import AI.PathFinder;
import Entity.*;
import Main.UI.UI;
import Object.*;
import Main.Tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{

    //Screen Settings
    final int originalTileSize = 16;// 16x16 tiles
    final int scale = 3;// 3x scale, 16x16 tiles * 3 = 48x48 pixels
    public final int tileSize = originalTileSize * scale;// 48x48 pixels
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;// 768 pixels
    public final int screenHeight = tileSize * maxScreenRow;// 576 pixels

    //World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;// 2400 pixels
    public final int worldHeight = tileSize * maxWorldRow;// 2400 pixels

    final int FPS = 60;

    //Controllers of the game
    KeyHandler keyHandler = new KeyHandler(this);
    Sound sound = new Sound();
    public TileManager tileManager = new TileManager(this);
    public CollisionDetector collisionDetector = new CollisionDetector(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public PathFinder pathFinder = new PathFinder(this);
    Thread gameThread;

    //entities and objects
    public Player player = new Player(this, keyHandler);
    public Entity[] monsters = new Entity[50];
    public SuperObject[] objects = new SuperObject[50];
    ArrayList<Entity> entityList = new ArrayList<Entity>();
    public ArrayList<Entity> flyingObjectList = new ArrayList<Entity>();

    //game states
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int gameOverState = 3;

    public GamePanel(){
        setFocusable(true);
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
    }

    /**
     * set up the objects, npc, etc.
     */
    public void setupGame() {
        assetSetter.setMonsters();

        playMusic(0);
        gameState = playState;
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
//                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            //Update the player
            player.update();

            //Update the monsters
            for (int i = 0; i < monsters.length; i++) {
                if (monsters[i] != null) {
                    if (monsters[i].alive && !monsters[i].dying) {
                        monsters[i].update();
                    }
                    if (!monsters[i].alive) {
                        monsters[i] = null;
                    }
                }
            }

            for (int i = 0; i < flyingObjectList.size(); i++) {
                if (flyingObjectList.get(i) != null) {
                    if (flyingObjectList.get(i).alive) {
                        flyingObjectList.get(i).update();
                    }
                    if (!flyingObjectList.get(i).alive) {
                        flyingObjectList.remove(i);
                    }
                }
            }


        } else {
            //todo
        }
    }

    public void paintComponent(Graphics g) {//Draw the screen
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        //Draw the world
        tileManager.draw(g2d);

        //Draw the player
        player.draw(g2d);

        //Draw the monsters
        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i] != null) {
                monsters[i].draw(g2d);
            }
        }

        for (int i = 0; i < flyingObjectList.size(); i++) {
            if (flyingObjectList.get(i) != null) {
                flyingObjectList.get(i).draw(g2d);
            }
        }


        //UI
        ui.draw(g2d);

        g2d.dispose();
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    /**
     * about sound effect
     */
    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }
}
