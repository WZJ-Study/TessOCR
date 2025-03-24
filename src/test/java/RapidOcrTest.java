import cc.wangzijie.demo.utils.ImageUtils;
import com.benjaminwan.ocrlibrary.OcrInput;
import com.benjaminwan.ocrlibrary.OcrResult;
import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.InferenceEngine;
import io.github.mymonstercat.ocr.config.ParamConfig;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class RapidOcrTest {

    public static void main(String[] args) throws Exception {
        InferenceEngine engine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V4);
        String imageFormat = "png";

        System.out.println("==== 原始图像 ====");
        OcrResult ocrResultA = engine.runOcr("D:/cccc.png");
        System.out.println(ocrResultA.getStrRes());


        System.out.println("==== 放大2倍 ====");
        BufferedImage rectImage = ImageIO.read(new File("D:/cccc.png"));
        BufferedImage resizedImage = ImageUtils.scaleImage(rectImage, 2.0);
        ImageIO.write(resizedImage, imageFormat, new File("D:/cccc2.png"));
        OcrResult ocrResult2 = engine.runOcr("D:/cccc2.png");
        System.out.println(ocrResult2.getStrRes());
    }

}
