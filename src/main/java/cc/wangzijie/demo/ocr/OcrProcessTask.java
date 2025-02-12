package cc.wangzijie.demo.ocr;

import cc.wangzijie.demo.snapshot.SnapshotFileQueue;
import cc.wangzijie.demo.utils.JacksonUtils;
import cc.wangzijie.demo.vo.OcrProcessResult;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.IOUtils;
import org.springframework.util.CollectionUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public class OcrProcessTask implements Runnable {

    /**
     * OCR识别引擎
     */
    private final Tesseract tesseract;

    /**
     * 待OCR识别的截图文件
     */
    private final File snapshotFile;

    /**
     * OCR识别框选区域
     */
    private final Map<String, Rectangle> ocrRectMap;



    public OcrProcessTask(Tesseract tesseract, File snapshotFile, Map<String, Rectangle> ocrRectMap) {
        this.tesseract = tesseract;
        this.snapshotFile = snapshotFile;
        this.ocrRectMap = ocrRectMap;

    }

    @Override
    public void run() {
        // 读取截屏图片文件
        BufferedImage image = null;
        try {
            image = ImageIO.read(snapshotFile);
            int height = image.getHeight();
            int width = image.getWidth();
            log.info("==== OCR识别处理 ==== 处理截屏图片文件【{}】，图片尺寸 height = {} width = {}", snapshotFile.getName(), height, width);
        } catch (IOException e) {
            log.error("==== OCR识别处理 ==== 截屏图片文件打开失败，捕获到异常！", e);
        }

        // 处理该截屏图片
        if (null != image) {
            // 创建Graphics2D对象
            Graphics2D g2d = image.createGraphics();

            // 设置颜色和线宽
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(5));

            // OCR识别各框选区域
            List<OcrProcessResult> resultList = new LinkedList<>();
            for (String key : this.ocrRectMap.keySet()) {
                try {
                    Rectangle rect = this.ocrRectMap.get(key);
                    String result = tesseract.doOCR(image, null, Collections.singletonList(rect));
                    log.info("==== OCR识别处理 ==== 截屏图片文件OCR识别成功，区域：{} \n==> 坐标范围：{} \n==> 识别结果：{}", key, rect, result);
                    resultList.add(new OcrProcessResult(key, result));

                    // 画矩形框
                    g2d.draw(rect);

                    // 画文本
                    g2d.drawString(key, (int) rect.getX(), (int) rect.getY());
                } catch (TesseractException e) {
                    log.error("==== OCR识别处理 ==== 截屏图片文件OCR识别失败，捕获到异常！", e);
                }
            }

            // 释放图形上下文使用的资源
            g2d.dispose();

            // 保存修改后的图片
            try {
                String filePath = snapshotFile.getAbsolutePath() + ".draw.jpg";
                ImageIO.write(image, "jpg", new File(filePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // JSON文件保存OCR识别结果
            this.outputAsJsonFile(resultList);
        }
    }


    private void outputAsJsonFile(List<OcrProcessResult> resultList) {
        if (CollectionUtils.isEmpty(resultList)) {
            return;
        }
        String json = JacksonUtils.toJSONStringPretty(resultList);
        String filePath = snapshotFile.getAbsolutePath() + ".json";
        try (OutputStream os = new FileOutputStream(filePath)) {
            IOUtils.write(json, os, Charset.defaultCharset());
        } catch (IOException e) {
            log.error("==== OCR识别处理 ==== JSON文件保存OCR识别结果失败，捕获到异常！", e);
        }
    }


}
