package ResourceLoader;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * AudioLoader 类用于加载和缓存游戏中的音频资源。
 * 继承自 ResourceLoader，利用其资源缓存机制。
 */
public class AudioLoader extends ResourceLoader {

        /**
         * 构造函数，初始化音频资源缓存。
         */
        public AudioLoader() {
            super(); // 调用父类的构造函数初始化资源缓存
        }

        /**
         * 加载音频资源，并进行缓存。
         * @param path 音频文件的路径
         * @return 加载成功时返回 Clip 对象，加载失败时返回 null
         */
        @Override
        public Clip loadAudio(String path) {
            // 如果音频已经被加载过，直接返回缓存的音频
            if (super.getAudioClips().containsKey(path)) {
                return super.getAudioClips().get(path);
            } else {
                try {
                    // 尝试从文件加载音频
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    // 将加载的音频缓存起来
                    super.getAudioClips().put(path, clip);
                    // 返回加载成功的音频
                    return clip;
                } catch (Exception e) {
                    // 音频加载失败时打印错误信息并返回 null
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

