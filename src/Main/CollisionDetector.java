package Main;

import Entity.Entity;
import Entity.Direction;
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

        int tileNum1, tileNum2,tileNum3;//only two tiles are to be detected, for example if the entity is moving up, only the tile above and the tile above and to the right are to be detected

        switch (entity.direction) {
            case U -> {
                entityUpRow = (entityUpY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityUpRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityUpRow];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case D -> {
                entityDownRow = (entityDownY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityDownRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityDownRow];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case L -> {
                entityLeftCol = (entityLeftX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityUpRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityDownRow];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case R -> {
                entityRightCol = (entityRightX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityUpRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityDownRow];
                if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case RU -> {
                entityRightCol = (entityRightX + entity.speed) / gp.tileSize;
                entityUpRow = (entityUpY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityUpRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityUpY / gp.tileSize];
                tileNum3 = gp.tileManager.mapTileNum[gp.currentMap][entityRightX / gp.tileSize][entityUpRow];
                if (gp.tileManager.tile[tileNum1].collision) {
                    entity.collisionOn = true;
                }
                if (entity.collisionOn == true && !(gp.tileManager.tile[tileNum2].collision)) {
                    entity.collisionOn = false;
                    entity.direction = Direction.R;
                } else if (entity.collisionOn == true && !(gp.tileManager.tile[tileNum3].collision)) {
                    entity.collisionOn = false;
                    entity.direction = Direction.U;
                }
            }
            case RD -> {
                entityRightCol = (entityRightX + entity.speed) / gp.tileSize;
                entityDownRow = (entityDownY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityDownRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityUpY / gp.tileSize];
                tileNum3 = gp.tileManager.mapTileNum[gp.currentMap][entityRightX / gp.tileSize][entityDownRow];
                if (gp.tileManager.tile[tileNum1].collision) {
                    entity.collisionOn = true;
                }
                if (entity.collisionOn == true && !(gp.tileManager.tile[tileNum2].collision)) {
                    entity.collisionOn = false;
                    entity.direction = Direction.R;
                } else if (entity.collisionOn == true && !(gp.tileManager.tile[tileNum3].collision)) {
                    entity.collisionOn = false;
                    entity.direction = Direction.D;
                }
            }
            case LD -> {
                entityLeftCol = (entityLeftX - entity.speed) / gp.tileSize;
                entityDownRow = (entityDownY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityDownRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftX / gp.tileSize][entityDownRow];
                tileNum3 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityDownY / gp.tileSize];
                if (gp.tileManager.tile[tileNum1].collision) {
                    entity.collisionOn = true;
                }
                if (entity.collisionOn == true && !(gp.tileManager.tile[tileNum3].collision)) {
                    entity.collisionOn = false;
                    entity.direction = Direction.L;
                } else if (entity.collisionOn == true && !(gp.tileManager.tile[tileNum2].collision)) {
                    entity.collisionOn = false;
                    entity.direction = Direction.D;
                }
            }
            case LU -> {
                entityLeftCol = (entityLeftX - entity.speed) / gp.tileSize;
                entityUpRow = (entityUpY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityUpRow];
                tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityDownY / gp.tileSize];
                tileNum3 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftX / gp.tileSize][entityUpRow];
                if (gp.tileManager.tile[tileNum1].collision) {
                    entity.collisionOn = true;
                }
                if (entity.collisionOn == true && !(gp.tileManager.tile[tileNum2].collision)) {
                    entity.collisionOn = false;

                    entity.direction = Direction.L;
                } else if (entity.collisionOn == true && !(gp.tileManager.tile[tileNum3].collision)) {
                    entity.collisionOn = false;
                    entity.direction = Direction.U;
                }
            }
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int idx = 999;

        for (int i = 0; i < gp.objects[gp.currentMap].length; i ++) {
            if (gp.objects[gp.currentMap][i] != null) {
                //get entity's hitBox
                entity.hitBox.x = entity.worldLoc.getXPosition() + entity.hitBox.x;
                entity.hitBox.y = entity.worldLoc.getYPosition() + entity.hitBox.y;
                //get target's hitBox
                gp.objects[gp.currentMap][i].hitBox.x = gp.objects[gp.currentMap][i].worldLoc.getXPosition() + gp.objects[gp.currentMap][i].hitBox.x;
                gp.objects[gp.currentMap][i].hitBox.y = gp.objects[gp.currentMap][i].worldLoc.getYPosition() + gp.objects[gp.currentMap][i].hitBox.y;

                switch (entity.direction) {
                    case U -> entity.hitBox.y -= entity.speed;
                    case D -> entity.hitBox.y += entity.speed;
                    case L -> entity.hitBox.x -= entity.speed;
                    case R -> entity.hitBox.x += entity.speed;
                    case RU -> {
                        entity.hitBox.x += entity.speed;
                        entity.hitBox.y -= entity.speed;
                    }
                    case RD -> {
                        entity.hitBox.x += entity.speed;
                        entity.hitBox.y += entity.speed;
                    }
                    case LD -> {
                        entity.hitBox.x -= entity.speed;
                        entity.hitBox.y += entity.speed;
                    }
                    case LU -> {
                        entity.hitBox.x -= entity.speed;
                        entity.hitBox.y -= entity.speed;
                    }
                }
                if (entity.hitBox.intersects(gp.objects[gp.currentMap][i].hitBox)) {
                    entity.collisionOn = true;
                    idx = i;
                }

                entity.hitBox.x = entity.hitBoxDefaultX;
                entity.hitBox.y = entity.hitBoxDefaultY;
                gp.objects[gp.currentMap][i].hitBox.x = gp.objects[gp.currentMap][i].hitBoxDefaultX;
                gp.objects[gp.currentMap][i].hitBox.y = gp.objects[gp.currentMap][i].hitBoxDefaultY;
            }
        }

        //return the index of the object that the entity is colliding with
        return idx;
    }

    public int checkEntity(Entity entity, Entity[] target) {
        int idx = 999;
        //record the position of the entity's hitBox
        for (int i = 0; i < target.length; i ++) {
            if (target[i] != null) {
                //get entity's hitBox
                entity.hitBox.x = entity.worldLoc.getXPosition() + entity.hitBox.x;
                entity.hitBox.y = entity.worldLoc.getYPosition() + entity.hitBox.y;
                //get target's hitBox
                target[i].hitBox.x = target[i].worldLoc.getXPosition() + target[i].hitBox.x;
                target[i].hitBox.y = target[i].worldLoc.getYPosition() + target[i].hitBox.y;

                switch (entity.direction) {
                    case U -> entity.hitBox.y -= entity.speed;
                    case D -> entity.hitBox.y += entity.speed;
                    case L -> entity.hitBox.x -= entity.speed;
                    case R -> entity.hitBox.x += entity.speed;
                    case RU -> {
                        entity.hitBox.x += entity.speed;
                        entity.hitBox.y -= entity.speed;
                    }
                    case RD -> {
                        entity.hitBox.x += entity.speed;
                        entity.hitBox.y += entity.speed;
                    }
                    case LD -> {
                        entity.hitBox.x -= entity.speed;
                        entity.hitBox.y += entity.speed;
                    }
                    case LU -> {
                        entity.hitBox.x -= entity.speed;
                        entity.hitBox.y -= entity.speed;
                    }
                }
                if (entity.hitBox.intersects(target[i].hitBox) && target[i] != entity) {
                    entity.collisionOn = true;
                    idx = i;
                }

                entity.hitBox.x = entity.hitBoxDefaultX;
                entity.hitBox.y = entity.hitBoxDefaultY;
                target[i].hitBox.x = target[i].hitBoxDefaultX;
                target[i].hitBox.y = target[i].hitBoxDefaultY;
            }
        }

        return idx;
    }

    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;
        //get entity's hitBox
        entity.hitBox.x = entity.worldLoc.getXPosition() + entity.hitBox.x;
        entity.hitBox.y = entity.worldLoc.getYPosition() + entity.hitBox.y;
        //get target's hitBox
        gp.player.hitBox.x = gp.player.worldLoc.getXPosition() + gp.player.hitBox.x;
        gp.player.hitBox.y = gp.player.worldLoc.getYPosition() + gp.player.hitBox.y;

        switch (entity.direction) {
            case U -> entity.hitBox.y -= entity.speed;
            case D -> entity.hitBox.y += entity.speed;
            case L -> entity.hitBox.x -= entity.speed;
            case R -> entity.hitBox.x += entity.speed;
            case RU -> {
                entity.hitBox.x += entity.speed;
                entity.hitBox.y -= entity.speed;
            }
            case RD -> {
                entity.hitBox.x += entity.speed;
                entity.hitBox.y += entity.speed;
            }
            case LD -> {
                entity.hitBox.x -= entity.speed;
                entity.hitBox.y += entity.speed;
            }
            case LU -> {
                entity.hitBox.x -= entity.speed;
                entity.hitBox.y -= entity.speed;
            }
        }
        if (entity.hitBox.intersects(gp.player.hitBox)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }


        entity.hitBox.x = entity.hitBoxDefaultX;
        entity.hitBox.y = entity.hitBoxDefaultY;
        gp.player.hitBox.x = gp.player.hitBoxDefaultX;
        gp.player.hitBox.y = gp.player.hitBoxDefaultY;

        return contactPlayer;
    }
}
