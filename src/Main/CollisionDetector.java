package Main;

import Entity.Entity;

public class CollisionDetector {
    GamePanel gp;

    public CollisionDetector(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        //record the position of the entity's hitBox
        int entityLeftX = entity.worldLoc.getXPosition() + entity.hitBox.x;
        int entityRightX = entity.worldLoc.getXPosition() + entity.hitBox.x + entity.hitBox.width;
        int entityUpY = entity.worldLoc.getYPosition() + entity.hitBox.y;
        int entityDownY = entity.worldLoc.getYPosition() + entity.hitBox.y + entity.hitBox.height;

        int entityLeftCol = entityLeftX / gp.tileSize;
        int entityRightCol = entityRightX / gp.tileSize;
        int entityUpRow = entityUpY / gp.tileSize;
        int entityDownRow = entityDownY / gp.tileSize;

        int tileNum1, tileNum2;//only two tiles are to be detected, for example if the entity is moving up, only the tile above and the tile above and to the right are to be detected

        switch (entity.direction) {
            case U -> {
                entityUpRow = (entityUpY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityUpRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityUpRow];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case D -> {
                entityDownRow = (entityDownY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityDownRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityDownRow];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case L -> {
                entityLeftCol = (entityLeftX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityUpRow];
                tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityDownRow];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case R -> {
                entityRightCol = (entityRightX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityUpRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityDownRow];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case RU -> {
                entityRightCol = (entityRightX + entity.speed) / gp.tileSize;
                entityUpRow = (entityUpY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityUpRow];
                //tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityDownRow];
//                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
//                    entity.collisionOn = true;
//                }
                if (gp.tileManager.tile[tileNum1].collision ) {
                    entity.collisionOn = true;
                }
            }
            case RD -> {
                entityRightCol = (entityRightX + entity.speed) / gp.tileSize;
                entityDownRow = (entityDownY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityDownRow];
                //tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityDownRow];
//                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
//                    entity.collisionOn = true;
//                }
                if (gp.tileManager.tile[tileNum1].collision ) {
                    entity.collisionOn = true;
                }
            }
            case LD -> {
                entityLeftCol = (entityLeftX - entity.speed) / gp.tileSize;
                entityDownRow = (entityDownY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityDownRow];
                //tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityDownRow];
//                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
//                    entity.collisionOn = true;
//                }
                if (gp.tileManager.tile[tileNum1].collision ) {
                    entity.collisionOn = true;
                }
            }
            case LU -> {
                entityLeftCol = (entityLeftX - entity.speed) / gp.tileSize;
                entityUpRow = (entityUpY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityUpRow];
                //tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityDownRow];
//                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
//                    entity.collisionOn = true;
//                }
                if (gp.tileManager.tile[tileNum1].collision ) {
                    entity.collisionOn = true;
                }
            }
        }
    }
}
