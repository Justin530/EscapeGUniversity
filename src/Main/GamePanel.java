package Main;

import AI.PathFinder;
import Entity.*;
import Entity.Monsters.Yukari;
import Main.UI.EndingStory;
import Main.UI.Story;
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
    public final int maxMap = 10;
    public int currentMap = 0;

    final int FPS = 60;

    //Controllers of the game
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound sound = new Sound();
    public TileManager tileManager = new TileManager(this);
    public CollisionDetector collisionDetector = new CollisionDetector(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public PathFinder pathFinder = new PathFinder(this);
    Thread gameThread;

    //entities and objects
    public Player player = new Player(this, keyHandler);
    public Entity[][] monsters = new Entity[maxMap][50];
    public SuperObject[][] objects = new SuperObject[maxMap][50];
    ArrayList<Entity> entityList = new ArrayList<Entity>();
    public ArrayList<Entity> flyingObjectList = new ArrayList<Entity>();
    public Story story = new Story(this);
    public EndingStory endingStory = new EndingStory(this);

    //game states
    public int gameState = -1;
    public final int storyState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int gameOverState = 3;
    public final int characterState = 4;
    public final int dialogueState = 5;
    public final int endingState = 6;
    public final int victoryState = 7;

    public GamePanel(){
        setFocusable(true);
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(new Color(84, 164, 31));
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
    }

    /**
     * set up the objects, npc, etc.
     */
    public void setupGame() {
        assetSetter.setMonsters();
        assetSetter.setObjects();

        playMusic(1);
        gameState = storyState;
    }

    public void restart() {
        assetSetter.setMonsters();
        assetSetter.setObjects();
        player.setDefaultValues();

        stopMusic();
        playMusic(1);
        gameState = storyState;
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
            for (int i = 0; i < monsters[currentMap].length; i++) {
                if (monsters[currentMap][i] != null) {
                    if (monsters[currentMap][i].alive && !monsters[currentMap][i].dying) {
                        monsters[currentMap][i].update();
                    }
                    if (!monsters[currentMap][i].alive) {
                        if(monsters[currentMap][i] instanceof Yukari)
                            ((Yukari) monsters[currentMap][i]).dropObject();
                        monsters[currentMap][i] = null;
                    }
                }
            }

            for (int i = 0; i < flyingObjectList.size(); i++) {
                if (flyingObjectList.get(i) != null) {
                    if (flyingObjectList.get(i).alive && !flyingObjectList.get(i).dying) {
                        flyingObjectList.get(i).update();
                    }
                    if (!flyingObjectList.get(i).alive) {
                        flyingObjectList.remove(i);
                    }
                }
            }


        } else if (gameState == storyState){
            //todo
            story.speak();
        } else if (gameState == dialogueState) {
            objects[currentMap][player.objectIndex].interact();
        } else if (gameState == endingState) {
            //todo
            endingStory.speak();
        }
    }

    public void paintComponent(Graphics g) {//Draw the screen
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        if (gameState != storyState) {
            //Draw the world
            tileManager.draw(g2d);

            for (int i = 0; i < objects[currentMap].length; i++) {
                if (objects[currentMap][i] != null) {
                    objects[currentMap][i].draw(g2d, this);
                }
            }

            //Draw the player
            player.draw(g2d);

            //Draw the monsters
            for (int i = 0; i < monsters[currentMap].length; i++) {
                if (monsters[currentMap][i] != null) {
                    monsters[currentMap][i].draw(g2d);
                }
            }

            for (int i = 0; i < flyingObjectList.size(); i++) {
                if (flyingObjectList.get(i) != null) {
                    flyingObjectList.get(i).draw(g2d);
                }
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
