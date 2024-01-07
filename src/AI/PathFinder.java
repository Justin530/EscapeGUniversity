package AI;

import Main.GamePanel;
import Entity.*;

import java.util.ArrayList;

public class PathFinder {
    GamePanel gp;
    Node[][] nodes;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalFound = false;
    int step = 0;

    public PathFinder(GamePanel gp) {
        this.gp = gp;
        initializeNodes();
    }

    public void initializeNodes() {
        nodes = new Node[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0, row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            nodes[col][row] = new Node(col, row);
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void resetNodes() {
        int col = 0, row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            //reset open, checked and solid state
            nodes[col][row].open = false;
            nodes[col][row].checked = false;
            nodes[col][row].solid = false;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }

        //reset other settings
        openList.clear();
        pathList.clear();
        goalFound = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();

        startNode = nodes[startCol][startRow];
        goalNode = nodes[goalCol][goalRow];
        currentNode = startNode;
        openList.add(currentNode);

        int col = 0, row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            //set solid node
            //check tiles
            int tileNum = gp.tileManager.mapTileNum[gp.currentMap][col][row];
            if (gp.tileManager.tile[tileNum].collision) {
                nodes[col][row].solid = true;
            }
            //check interactive tiles
            //todo

            //set cost
            getCost(nodes[col][row]);

            col ++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row ++;
            }
        }
    }

    public void getCost(Node node) {
        node.gCost = Math.abs(node.col - startNode.col) + Math.abs(node.row - startNode.row);
        node.hCost = Math.abs(node.col - goalNode.col) + Math.abs(node.row - goalNode.row);
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {
        while (!goalFound && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            //check the current node
            currentNode.checked = true;
            openList.remove(currentNode);

            //Open the up node
            if (row - 1 >= 0) {
                openNode(nodes[col][row - 1]);
            }
            //Open the down node
            if (row + 1 < gp.maxWorldRow) {
                openNode(nodes[col][row + 1]);
            }
            //Open the left node
            if (col - 1 >= 0) {
                openNode(nodes[col - 1][row]);
            }
            //Open the right node
            if (col + 1 < gp.maxWorldCol) {
                openNode(nodes[col + 1][row]);
            }

            //find the best node
            int bestNodeIndex = 0;
            int bestNodeFCost = Integer.MAX_VALUE;

            for (int i = 0; i < openList.size(); i++) {
                //check if this node's fCost is better
                if (openList.get(i).fCost < bestNodeFCost) {
                    bestNodeFCost = openList.get(i).fCost;
                    bestNodeIndex = i;
                }
                //if fCost is equal, check the gCost
                else if (openList.get(i).fCost == bestNodeFCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            //if there is no node in the open list, end this loop
            if (openList.size() == 0) {
                break;
            }

            //after the loop, openList.get(bestNodeIndex) is the best node
            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalFound = true;
                trackThePath();
            }

            step++;
        }

        return goalFound;
    }

    public void openNode(Node node) {
        if (!node.solid && !node.checked && !node.open) {
            node.open = true;
            openList.add(node);
            node.parent = currentNode;
        }
    }

    public void trackThePath() {
        Node node = goalNode;
        while (node != startNode) {
            pathList.add(0, node);
            node = node.parent;
        }
    }
}
