package ResourceLoader;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"all"})

/**
 * ResourceLoader类用于加载和缓存游戏中的图像和音频资源。
 */
public class ResourceLoader {
    // 用于缓存已加载的图像资源
    private Map<String, Image> images;
    // 用于缓存已加载的音频资源
    private Map<String, Clip> audioClips;

    public Map<String, Image> getImages() {
        return images;
    }

    public Map<String, Clip> getAudioClips() {
        return audioClips;
    }

    /**
     * 构造函数，初始化资源
     */
    public ResourceLoader() {
        images = new HashMap<>();
        audioClips = new HashMap<>();
    }

    /**
     * 加载图像资源，并进行缓存。
     * @param path 图像文件的路径
     * @return 加载成功时返回Image对象，加载失败时返回null
     */
    public Image loadImage(String path) {
        // 先检测，如果图像已经被加载过，直接返回缓存的图像
        if (images.containsKey(path)) {
            return images.get(path);
        } else {
            try {
                // 尝试从文件加载图像
                Image image = ImageIO.read(new File(path));
                // 将加载的图像缓存起来
                images.put(path, image);
                // 返回加载成功的图像
                return image;
            } catch (IOException e) {
                // 图像加载失败时打印错误信息并返回null
                e.printStackTrace();
                return null;
            }
        }
    }


    /**
     * 加载音频资源，并进行缓存。
     * @param path 音频文件的路径
     * @return 加载成功时返回Clip对象，加载失败时返回null
     */
    public Clip loadAudio(String path) {
        // 先检测，如果音频已经被加载过，直接返回缓存的音频
        if (audioClips.containsKey(path)) {
            return audioClips.get(path);
        } else {
            try {
                // 尝试从文件加载音频
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                // 将加载的音频缓存起来
                audioClips.put(path, clip);
                // 返回加载成功的音频
                return clip;
            } catch (Exception e) {
                // 音频加载失败时打印错误信息并返回null
                e.printStackTrace();
                return null;
            }
        }
    }
}
