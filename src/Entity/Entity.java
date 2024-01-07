package Entity;

import Entity.FlyingObject.FlyingObject;
import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Entity {
    public GamePanel gp;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attack_up1, attack_up2, attack_down1, attack_down2, attack_left1, attack_left2, attack_right1, attack_right2;
    public Rectangle hitBox = new Rectangle(0, 0, 48, 48);
    public Rectangle attackBox = new Rectangle(0, 0, 0, 0);
    public int hitBoxDefaultX, hitBoxDefaultY;

    //state
    public Location worldLoc = new Location(0, 0);
    public Direction direction;
    public int spriteNum = 1;//sprite number, decide which sprite to draw(1 or 2)
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean onPath = false;

    //counter
    public int actionLockCounter = 0;//lock the action for a certain time
    public int spriteCount = 0;//count for sprite animation
    public int invincibleCounter = 0;
    public int dyingCounter = 0;
    public int hpBarCounter = 0;
    public int shotAvailableCounter = 0;



    //character status
    public int type;//0:player, 1:monster
    public String name;
    public int maxHP;
    public int HP;
    public int speed;
    public int attack;
    public FlyingObject flyingObject = null;


    public Entity(GamePanel gp) {
        this.gp = gp;

        hitBox = new Rectangle(worldLoc.getXPosition(), worldLoc.getYPosition(), gp.tileSize, gp.tileSize);
    }

    public void setAction() {}
    public void damageReaction() {}
    public void checkCollision() {
        collisionOn = false;
        gp.collisionDetector.checkTile(this);
        gp.collisionDetector.checkEntity(this, gp.monsters[gp.currentMap]);
        boolean contactPlayer = gp.collisionDetector.checkPlayer(this);

        if (type == 1 && contactPlayer) {
            damagePlayer(attack);
        }
    }
    public void update() {
        setAction();

        checkCollision();

        //if collision is detected, player cannot move
        if (!collisionOn) {
            switch (direction) {
                case U -> worldLoc.setYPosition(worldLoc.getYPosition() - speed);
                case D -> worldLoc.setYPosition(worldLoc.getYPosition() + speed);
                case L -> worldLoc.setXPosition(worldLoc.getXPosition() - speed);
                case R -> worldLoc.setXPosition(worldLoc.getXPosition() + speed);
                case LD -> {
                    worldLoc.setXPosition(worldLoc.getXPosition() - speed);
                    worldLoc.setYPosition(worldLoc.getYPosition() + speed);
                }
                case RD -> {
                    worldLoc.setYPosition(worldLoc.getYPosition() + speed);
                    worldLoc.setXPosition(worldLoc.getXPosition() + speed);
                }
                case LU -> {
                    worldLoc.setXPosition(worldLoc.getXPosition() - speed);
                    worldLoc.setYPosition(worldLoc.getYPosition() - speed);
                }
                case RU -> {
                    worldLoc.setXPosition(worldLoc.getXPosition() + speed);
                    worldLoc.setYPosition(worldLoc.getYPosition() - speed);
                }
            }
        }

        if (spriteCount == 10) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCount = 0;
        } else {
            spriteCount++;
        }

        if (invincible) {
            invincibleCounter ++;
            if (invincibleCounter >= 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void damagePlayer(int attack) {
        if (!gp.player.invincible) {
            gp.player.HP -= attack;
            gp.player.invincible = true;
        }
    }

    public void draw(Graphics2D g2d) {
        BufferedImage img = null;
        int worldX = worldLoc.getXPosition();
        int worldY = worldLoc.getYPosition();

        int screenX = worldX - gp.player.worldLoc.getXPosition() + gp.player.screenLoc.getXPosition();
        int screenY = worldY - gp.player.worldLoc.getYPosition() + gp.player.screenLoc.getYPosition();

        if (worldX + gp.tileSize > gp.player.worldLoc.getXPosition() - gp.player.screenLoc.getXPosition() &&
            worldX - gp.tileSize < gp.player.worldLoc.getXPosition() + gp.player.screenLoc.getXPosition() &&
            worldY + gp.tileSize > gp.player.worldLoc.getYPosition() - gp.player.screenLoc.getYPosition() &&
            worldY - gp.tileSize < gp.player.worldLoc.getYPosition() + gp.player.screenLoc.getYPosition()) {

            img = switch (direction) {
                case LU, U, RU -> (spriteNum == 1) ? up1 : (spriteNum == 2) ? up2 : null;
                case LD, D, RD -> (spriteNum == 1) ? down1 : (spriteNum == 2) ? down2 : null;
                case L -> (spriteNum == 1) ? left1 : (spriteNum == 2) ? left2 : null;
                case R -> (spriteNum == 1) ? right1 : (spriteNum == 2) ? right2 : null;
                default -> null;
            };
            //monster HP bar
            if (type == 1 && hpBarOn) {
                double oneScale = (double)gp.tileSize / maxHP;
                double hpBarScale = oneScale * HP;

                g2d.setColor(new Color(35, 35, 35));
                g2d.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);

                g2d.setColor(new Color(255, 0, 50));
                g2d.fillRect(screenX, screenY - 15, (int)hpBarScale, 10);

                hpBarCounter ++;
                if (hpBarCounter >= 600) {
                    hpBarOn = false;
                    hpBarCounter = 0;
                }
            }

            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2d, 0.5f);
            }
            if (dying) {
                dyingAnimation(g2d);
            }
            g2d.drawImage(img, screenX, screenY, gp.tileSize, gp.tileSize, null);
            //reset alpha
            changeAlpha(g2d, 1.0f);
        }
    }

    public void dyingAnimation(Graphics2D g2d) {
        dyingCounter ++;

        if (dyingCounter <= 5) {
            changeAlpha(g2d, 0.0f);
        } else if (dyingCounter <= 10) {
            changeAlpha(g2d, 1.0f);
        } else if (dyingCounter <= 15) {
            changeAlpha(g2d, 0.0f);
        } else if (dyingCounter <= 20) {
            changeAlpha(g2d, 1.0f);
        } else if (dyingCounter <= 25) {
            changeAlpha(g2d, 0.0f);
        } else if (dyingCounter <= 30) {
            changeAlpha(g2d, 1.0f);
        } else if (dyingCounter <= 35) {
            changeAlpha(g2d, 0.0f);
        } else if (dyingCounter <= 40) {
            changeAlpha(g2d, 1.0f);
        } else {
            dying = false;
            alive = false;
            dyingCounter = 0;
        }
    }
    public void changeAlpha(Graphics2D g2d, float alpha) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            image = utilityTool.scaleImage(image, width, height);
        } catch (IOException e) {
            System.out.println("Error reading player image");
        }

        return image;
    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldLoc.getXPosition() + hitBox.x) / gp.tileSize;
        int startRow = (worldLoc.getYPosition() + hitBox.y) / gp.tileSize;

        gp.pathFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gp.pathFinder.search()) {
            //next worldX and worldY
            int nextX = gp.pathFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pathFinder.pathList.get(0).row * gp.tileSize;

            //entity's hitBox position
            int entityLeftX = worldLoc.getXPosition() + hitBox.x;
            int entityRightX = worldLoc.getXPosition() + hitBox.x + hitBox.width;
            int entityTopY = worldLoc.getYPosition() + hitBox.y;
            int entityBottomY = worldLoc.getYPosition() + hitBox.y + hitBox.height;

            if (entityTopY > nextY && entityLeftX >= nextX && entityRightX < nextX + gp.tileSize) {
                direction = Direction.U;
            } else if (entityBottomY < nextY + gp.tileSize && entityLeftX >= nextX && entityRightX < nextX + gp.tileSize) {
                direction = Direction.D;
            } else if (entityLeftX > nextX && entityTopY >= nextY && entityBottomY < nextY + gp.tileSize) {
                direction = Direction.L;
            } else if (entityRightX < nextX + gp.tileSize && entityTopY >= nextY && entityBottomY < nextY + gp.tileSize) {
                direction = Direction.R;
            } else if (entityLeftX > nextX && entityTopY > nextY) {
                //up or left
                direction = Direction.U;
                checkCollision();
                if (collisionOn) {
                    direction = Direction.L;
                }
            } else if (entityRightX < nextX + gp.tileSize && entityTopY > nextY) {
                //up or right
                direction = Direction.U;
                checkCollision();
                if (collisionOn) {
                    direction = Direction.R;
                }
            } else if (entityLeftX > nextX && entityBottomY < nextY + gp.tileSize) {
                //down or left
                direction = Direction.D;
                checkCollision();
                if (collisionOn) {
                    direction = Direction.L;
                }
            } else if (entityRightX < nextX + gp.tileSize && entityBottomY < nextY + gp.tileSize) {
                //down or right
                direction = Direction.D;
                checkCollision();
                if (collisionOn) {
                    direction = Direction.R;
                }
            }

            //if reach the goal, stop searching
//            int nextCol = gp.pathFinder.pathList.get(0).col;
//            int nextRow = gp.pathFinder.pathList.get(0).row;
//            if (nextCol == goalCol && nextRow == goalRow) {
//                onPath = false;
//            }
        }
    }
}
