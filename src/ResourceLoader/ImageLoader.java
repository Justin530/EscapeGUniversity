package ResourceLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * ImageLoader 类用于加载和缓存游戏中的图像资源。
 * 继承自 ResourceLoader，利用其资源缓存机制。
 */
public class ImageLoader extends ResourceLoader {
    /**
     * 构造函数，初始化图像资源缓存。
     */
    public ImageLoader() {
        super();
    }
    /**
     * 加载图像资源，并进行缓存。
     * @param path 图像文件的路径
     * @return 加载成功时返回 Image 对象，加载失败时返回 null
     */
    @Override
    public Image loadImage(String path) {
        // 如果图像已经被加载过，直接返回缓存的图像
        if (super.getImages().containsKey(path)) {
            return super.getImages().get(path);
        } else {
            try {
                // 尝试从文件加载图像
                Image image = ImageIO.read(new File(path));
                // 将加载的图像缓存起来
                super.getImages().put(path, image);
                // 返回加载成功的图像
                return image;
            } catch (IOException e) {
                // 图像加载失败时打印错误信息并返回 null
                e.printStackTrace();
                return null;
            }
        }
    }
}
