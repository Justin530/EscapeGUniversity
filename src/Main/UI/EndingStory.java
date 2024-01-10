package Main.UI;

import Main.Game;
import Main.GamePanel;
import Main.KeyHandler;

public class EndingStory {
    String[] story = new String[30];
    GamePanel gp;
    int dialogueIndex = 0;

    public EndingStory(GamePanel gp){
        this.gp = gp;
        setStory();
    }

    public void setStory() {
        story[0] = "";
        story[1] = "最后，望着Rajiang们的尸体，\n你心有余悸地走入地下的安全屋";
        story[2] = "可是这里并不是什么安全屋，\n里面有的只是凌乱破碎的实验器材，\n和一台似乎还能启动的电脑";
        story[3] = "你终于找到了真相，\n这一切都是因为一个叫做“G大Lab”的实验室，\n他们在这里进行了一项名为“Rajiang”的实验。";
        story[4] = "Rajiang的生命力极其顽强，\n他们的身体可以自我修复，\n并且可以吸收周围的物质来增强自己的力量。";
        story[5] = "但是，\n这项实验并没有成功，\nRajiang们的身体并没有得到控制，\n他们开始攻击实验室的工作人员，\n并且逐渐蔓延到了整个城市。";
        story[6] = "这时，你收到消息说“G大Lab”秘密联系了军方，\n要将这里用核弹夷为平地，以掩盖他们的罪行";
        story[7] = "听着外面的Rajiang们的嘶吼，\n你决定要阻止这一切，\n你要去找到这个实验室，\n并且阻止他们的计划。";
    }

    public void speak() {
        if (story[dialogueIndex] == null) {
            dialogueIndex = 0;
            gp.gameState = gp.victoryState;
            gp.keyHandler.interactPressed = false;
            gp.stopMusic();
            gp.playMusic(2);
            return;
        }

        gp.ui.currentDialogue = story[dialogueIndex];
//            dialogueIndex ++;
//            gp.keyHandler.interactPressed = false;

    }
}