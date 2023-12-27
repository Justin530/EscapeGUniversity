package Main.Tile;

import Entity.Entity;
import Main.GamePanel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;// 2D array of tile numbers

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[50];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

        getTileImage();
        LoadMap("/res/map/map_demo.txt");
    }

    public void getTileImage() {
//        try {
//            // todo
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void LoadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0, row = 0;

            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                mapTileNum[col][row] = Integer.parseInt(br.readLine());
                col++;
                if (col == gp.maxScreenCol) {
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
        int col = 0, row = 0;
        int x = 0, y = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
            x = col * gp.tileSize;
            y = row * gp.tileSize;

            g2d.drawImage(tile[mapTileNum[col][row]].image, x, y, gp.tileSize, gp.tileSize, null);

            col++;
            if (col == gp.maxScreenCol) {
                col = 0;
                row++;
            }
        }
    }
}
