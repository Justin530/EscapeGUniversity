package Main.UI;

import Main.Game;
import Main.GamePanel;
import Main.KeyHandler;

public class Story {
    String[] story = new String[30];
    GamePanel gp;
    int dialogueIndex = 0;

    public Story(GamePanel gp){
        this.gp = gp;
        setStory();
    }

    public void setStory() {
        story[0] = "一个神秘的病毒在世界范围内爆发，\n导致人类变成了感染者，\n失去了理智和人性。";
        story[1] = "只有少数的幸存者还在坚持着，\n寻找着生存的希望和真相。";
        story[2] = "你是一名年轻的大学生，\n此时此刻正被困在G大中。";
        story[3] = "这时，你得知了一个地下安全屋的存在，\n那里有着幸存者的避难所，\n但是似乎去的过程注定无法安宁。";
        story[4] = "你需要穿过图书馆，但是站在图书馆门口，\n你听到里面若有若无的嘶吼声。\n你的身边还放着一个牌子，\n似乎是前人留下的遗迹";
        story[5] = "你的旅程就从这里开始了。";
    }

    public void speak() {

            if (story[dialogueIndex] == null) {
                dialogueIndex = 0;
                gp.gameState = gp.playState;
                gp.keyHandler.interactPressed = false;
                gp.stopMusic();
                gp.playMusic(0);
                return;
            }

            gp.ui.currentDialogue = story[dialogueIndex];
//            dialogueIndex ++;
//            gp.keyHandler.interactPressed = false;

    }
}
