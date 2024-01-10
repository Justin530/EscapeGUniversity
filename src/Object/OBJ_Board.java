package Object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Board extends SuperObject{

    public OBJ_Board(GamePanel gp){
        super(gp);
        name = "Board";

        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/object/note_board.png")));
            img = uTool.scaleImage(img, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setDialogues();
    }

    public void interact() {

            if (dialogues[dialogueIndex] == null) {
                dialogueIndex = 0;
                gp.gameState = gp.playState;
                gp.keyHandler.interactPressed = false;
                gp.player.objectIndex = 999;
                return;
            }

            gp.ui.currentDialogue = dialogues[dialogueIndex];
//            dialogueIndex ++;
//            gp.keyHandler.interactPressed = false;

    }

    public void setDialogues() {
        dialogues[0] = "";
        dialogues[1] = "当你看到这则消息时,\n我应该已经死了，\n但是我想给你一些引导";
        dialogues[2] = "你可以使用 wasd 来移动";
        dialogues[3] = "你有两把武器，\n一把是近战武器，一把是远程武器，\n按 j 键来进行攻击";
        dialogues[4] = "你可以通过按 k 键来切换武器,\n也可以通过按 l 键来使用大招";
        dialogues[5] = "贩卖机边上有些红瓶子，\n应该可以救你一命";
        dialogues[6] = "后来者啊，\n活下去，\n找到真相";
        dialogues[7] = "祝你 运";
    }
}
