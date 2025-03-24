package cc.wangzijie.demo.utils;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
public class ImageUtils {

    public static BufferedImage scaleImage(BufferedImage sourceImage, double factor) {
        try {
            // 创建缩放后的图像
            int newWidth = (int) (sourceImage.getWidth() * factor);
            int newHeight = (int) (sourceImage.getHeight() * factor);
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, sourceImage.getType());
            Graphics2D graphics = resizedImage.createGraphics();

            // 改善图像质量
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            // 设置透明度，保持原始图像的透明度
            graphics.setComposite(AlphaComposite.Src);

            // 绘制图像
            graphics.drawImage(sourceImage, 0, 0, newWidth, newHeight, null);
            graphics.dispose();
            return resizedImage;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toBytes(BufferedImage image, String imageFormat) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, imageFormat, os);
            return os.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
