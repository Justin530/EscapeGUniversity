package Main.Tile;

import Entity.Entity;
import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;// stores all types of tiles
    public int[][] mapTileNum;// 2D array of tile numbers
    ArrayList<String> fileNames = new ArrayList<String>();
    ArrayList<String> collisionStatus = new ArrayList<String>();

    public TileManager(GamePanel gp){
        this.gp = gp;

        //read tile information from file
        InputStream is = getClass().getResourceAsStream("/res/map/tiledata.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        //getting tile names and collision info from the file
        String line;

        try {
            while ((line = br.readLine()) != null) {
                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tile = new Tile[fileNames.size()];
        getTileImage();

        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        LoadMap("/res/map/worldmap.txt");
    }

    public void getTileImage() {
        try {
            for (int i = 0; i < fileNames.size(); i ++) {
                String fileName;
                boolean collision;

                //get a file name
                fileName = fileNames.get(i);

                //get collision status
                if (collisionStatus.get(i).equals("true")) {
                    collision = true;
                } else {
                    collision = false;
                }
                setup(i, fileName, collision);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setup(int idx, String imageName, boolean collision) {
        UtilityTool utilityTool = new UtilityTool();

        try {
            tile[idx] = new Tile();
            tile[idx].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tile/" + imageName)));
            tile[idx].image = utilityTool.scaleImage(tile[idx].image, gp.tileSize, gp.tileSize);
            tile[idx].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LoadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0, row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                String[] tokens = line.split(" ");

                for (String token : tokens) {
                    mapTileNum[col][row] = Integer.parseInt(token);
                    col++;
                }

                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        int worldCol = 0, worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldLoc.getXPosition() + gp.player.screenLoc.getXPosition();
            int screenY = worldY - gp.player.worldLoc.getYPosition() + gp.player.screenLoc.getYPosition();

            //Stop moving the camera at the edge of the world
            if (gp.player.screenLoc.getXPosition() > gp.player.worldLoc.getXPosition()) {
                screenX = worldX;
            }
            if (gp.player.screenLoc.getYPosition() > gp.player.worldLoc.getYPosition()) {
                screenY = worldY;
            }
            if (gp.screenWidth - gp.player.screenLoc.getXPosition() > gp.worldWidth - gp.player.worldLoc.getXPosition()) {
                screenX = gp.screenWidth - gp.worldWidth + worldX;
            }
            if (gp.screenHeight - gp.player.screenLoc.getYPosition() > gp.worldHeight - gp.player.worldLoc.getYPosition()) {
                screenY = gp.screenHeight - gp.worldHeight + worldY;
            }

            //Draw the tile
            if (worldX + gp.tileSize> gp.player.worldLoc.getXPosition() - gp.player.screenLoc.getXPosition() &&
                worldX - gp.tileSize< gp.player.worldLoc.getXPosition() + gp.screenWidth - gp.player.screenLoc.getXPosition() &&
                worldY + gp.tileSize> gp.player.worldLoc.getYPosition() - gp.player.screenLoc.getYPosition() &&
                worldY - gp.tileSize< gp.player.worldLoc.getYPosition() + gp.screenHeight - gp.player.screenLoc.getYPosition() &&
                tileNum != 0) {
                g2d.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            else if (gp.player.screenLoc.getXPosition() > gp.player.worldLoc.getXPosition() ||
                    gp.player.screenLoc.getYPosition() > gp.player.worldLoc.getYPosition() ||
                    gp.screenWidth - gp.player.screenLoc.getXPosition() > gp.worldWidth - gp.player.worldLoc.getXPosition() ||
                    gp.screenHeight - gp.player.screenLoc.getYPosition() > gp.worldHeight - gp.player.worldLoc.getYPosition()) {
                g2d.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
