import cc.wangzijie.demo.main.OCRManager;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OCRManagerTest {

    public static void main(String[] args) throws InterruptedException, AWTException {
        OCRManager ocrManager = new OCRManager();
        ocrManager.setIntervalSeconds(5);
        ocrManager.addOcrRect("section#1", new Rectangle(0, 0, 100, 100));
        ocrManager.addOcrRect("section#2", new Rectangle(100, 200, 100, 100));
        ocrManager.addOcrRect("section#3", new Rectangle(200, 300, 100, 100));
        ocrManager.addOcrRect("section#4", new Rectangle(300, 400, 100, 100));
        ocrManager.addOcrRect("section#5", new Rectangle(500, 600, 100, 100));
        log.info("==== 主线程 ==== 1. 启动OCR Manager");
        ocrManager.start();
        log.info("==== 主线程 ==== 1. 休眠 60 秒");
        TimeUnit.SECONDS.sleep(60);
        log.info("==== 主线程 ==== 2. 停止OCR Manager");
        ocrManager.stop();
        log.info("==== 主线程 ==== 2. 休眠 30 秒");
        TimeUnit.SECONDS.sleep(30);
        log.info("==== 主线程 ==== 3. 启动OCR Manager");
        ocrManager.start();
        log.info("==== 主线程 ==== 3. 休眠 60 秒");
        TimeUnit.SECONDS.sleep(60);
        log.info("==== 主线程 ==== 4. 停止OCR Manager");
        ocrManager.stop();
        log.info("==== 主线程 ==== 4. 结束");
    }

}
