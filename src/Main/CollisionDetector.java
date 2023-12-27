package Main;

import Entity.Entity;

public class CollisionDetector {
    GamePanel gp;

    public CollisionDetector(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        //record the position of the entity's hitBox
        int entityLeftX = entity.loc.getXPosition() + entity.hitBox.x;
        int entityRightX = entity.loc.getXPosition() + entity.hitBox.x + entity.hitBox.width;
        int entityUpY = entity.loc.getYPosition() + entity.hitBox.y;
        int entityDownY = entity.loc.getYPosition() + entity.hitBox.y + entity.hitBox.height;

        int entityLeftCol = entityLeftX / gp.tileSize;
        int entityRightCol = entityRightX / gp.tileSize;
        int entityUpRow = entityUpY / gp.tileSize;
        int entityDownRow = entityDownY / gp.tileSize;

        int tileNum1, tileNum2;//only two tiles are to be detected, for example if the entity is moving up, only the tile above and the tile above and to the right are to be detected

        switch (entity.direction) {
            case U:
                entityUpRow = (entityUpY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityUpRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityUpRow];

                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case D:
                entityDownRow = (entityDownY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityDownRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityDownRow];

                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case L:
                entityLeftCol = (entityLeftX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityUpRow];
                tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityDownRow];

                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case R:
                entityRightCol = (entityRightX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityUpRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityDownRow];

                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }
}
